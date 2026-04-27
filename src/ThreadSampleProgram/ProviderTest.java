package ThreadSampleProgram;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ProviderTest {
    
    private static int NUM_THREADS;
    private static final int NUM_TASKS = 1000000;
    private static final String FIELD_SEPARATOR = ",";
    
    private static final int[] DATA_LENGTHS = { 0, 3, 8, 18, 20, 23, 64, 128, 139, 142, 224, 235, 238, 256, 269, 384, 412,
            457, 512, 517, 586, 1024, 1183, 1196, 11976 };
    private static final int GCM_TAG_LENGTH = 128;
    
    private final BlockingQueue<Future<String>> results = new ArrayBlockingQueue<>(NUM_THREADS);
    
    private volatile boolean isDone = false;

    private Provider[] PROVIDERS;
    private final Map<Provider, List<List<String>>> ALGORITHMS;   
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {}
    }
    
    private final Map<String, Key> KEY_MAP = new HashMap<>();
    private final Map<String, KeyPair> PAIR_MAP = new HashMap<>();
    
    private final List<Callable<String>> CIPHER_OPERATIONS = new ArrayList<>();    
    {       
        
        // doFinal
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Cipher Operation doFinal() =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(0)) {
                        if(!isSymmetric(algorithm)) {
                            continue;
                        }
                        String pre = "Testcase: " + provider + FIELD_SEPARATOR + algorithm;
                        ByteArrayOutputStream baos = null;
                        try {
                            baos = new ByteArrayOutputStream();
                            Cipher cipher = Cipher.getInstance(algorithm, provider);
                            Key key = getKey(algorithm);
                            for (int length : DATA_LENGTHS) {
                                sb.append(pre)
                                        .append(FIELD_SEPARATOR)
                                        .append(length);
                                int padLength = getPaddingLength(algorithm, length, cipher.getBlockSize());
                              
                                if (padLength != 0) {
                                    sb.append("(padding ").append(padLength).append(")");
                                    length += padLength;
                                }
                                sb.append(" Result: ");
                                baos.reset();
                                try {
                                    byte[] input = new byte[length];
                                    rd.nextBytes(input);
                                    AlgorithmParameterSpec spec = getCipherParameterSpec(cipher);
                                    cipher = init(cipher, Cipher.ENCRYPT_MODE, key, spec);
                                    byte[] encrypted = cipher.doFinal(input);
                                    cipher = init(cipher, Cipher.DECRYPT_MODE, key, spec);
                                    byte[] output = cipher.doFinal(encrypted);
                                    sb.append(Arrays.equals(input, output)).append("\n");
                                } catch (Exception e) {
                                    e.printStackTrace(new PrintStream(baos));
                                    sb.append(baos);
                                    System.out.println("length = "+length+" algorithm = "+algorithm);
                                    System.exit(1);
                                    
                                }
                            }
                        } catch (Exception e) {
                            sb.append(pre).append(" Result: ");
                            e.printStackTrace(new PrintStream(baos));
                            sb.append(baos);
                            
                        }
                    }
                }
                return sb.toString();
            }
        };
        CIPHER_OPERATIONS.add(callable);
        
        // update... - doFinal
        callable = new Callable<String>() {
            @Override
            public String call() {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Cipher Operation update() - doFinal() =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(0)) {
                        if(!isSymmetric(algorithm)) {
                            continue;
                        }
                        sb.append("Testcase: ")
                                .append(provider)
                                .append(FIELD_SEPARATOR)
                                .append(algorithm);
                        ByteArrayOutputStream baos = null;
                        try {
                            baos = new ByteArrayOutputStream();
                            Cipher cipher = Cipher.getInstance(algorithm, provider);
                            Key key = getKey(algorithm);
                            AlgorithmParameterSpec spec = getCipherParameterSpec(cipher);
                            cipher = init(cipher, Cipher.ENCRYPT_MODE, key, spec);
                            ByteArrayOutputStream inputBuffer = new ByteArrayOutputStream();
                            ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
                            for (int i = 0; i < 3; i++) {
                                int length = DATA_LENGTHS[(int) (Math.random() * DATA_LENGTHS.length)];
                                byte[] data = new byte[length];
                                rd.nextBytes(data);
                                inputBuffer.write(data);
                                byte[] result = cipher.update(data);
                                if (result != null) {
                                    outputBuffer.write(result);
                                }
                                sb.append(FIELD_SEPARATOR)
                                        .append(length);
                            }
                            byte[] input = inputBuffer.toByteArray();
                            int padLength = getPaddingLength(algorithm, 
                                    inputBuffer.size(), 
                                    cipher.getBlockSize());
                            byte[] data = new byte[padLength];
                            outputBuffer.write(cipher.doFinal(data));
                            if (padLength != 0) {
                                sb.append("(padding ").append(padLength).append(")");
                            }
                            sb.append(" Result: ");
                            cipher = init(cipher, Cipher.DECRYPT_MODE, key, spec);
                            byte[] cipherText = outputBuffer.toByteArray();
                            outputBuffer.reset();
                            ByteArrayInputStream cipherStream = new ByteArrayInputStream(cipherText);
                            data = new byte[cipher.getBlockSize() != 0 ? cipher.getBlockSize() : 128];
                            int bytes = 0;
                            while ((bytes = cipherStream.read(data)) > 0) {
                                byte[] result = cipher.update(data, 0, bytes);
                                if (result != null) {
                                    outputBuffer.write(result);
                                }
                            }
                            outputBuffer.write(cipher.doFinal());
                            byte[] output = Arrays.copyOf(outputBuffer.toByteArray(), 
                                    inputBuffer.size());
                            sb.append(Arrays.equals(input, output)).append("\n");
                        } catch (Exception e) {
                            sb.append(" Result: ");
                            e.printStackTrace(new PrintStream(baos));
                            sb.append(baos);
                        }
                    }
                }
                return sb.toString();
            }
        };
        CIPHER_OPERATIONS.add(callable);
        
        // wrap - unwrap
        callable = new Callable<String>() {
            @Override
            public String call() {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Cipher Operation wrap() =========\n");
                Key baseKey = null;
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(0)) {
                        if(!isKeyWrap(provider, algorithm)) {
                            continue;
                        }
                        sb.append("Testcase: ")
                                .append(provider)
                                .append(FIELD_SEPARATOR)
                                .append(algorithm)
                                .append(FIELD_SEPARATOR)
                                .append("KeyWrap")
                                .append(" Result: ");
                        ByteArrayOutputStream baos = null;
                        try {
                            baos = new ByteArrayOutputStream();
                            if (baseKey == null) {
                                baseKey = getKey("AES");
                            }
                            Cipher baseCipher = Cipher.getInstance("AES");
                            baseCipher.init(Cipher.ENCRYPT_MODE, baseKey);
                            byte[] input = new byte[256];
                            rd.nextBytes(input);
                            byte[] cipherText = baseCipher.doFinal(input);
                            Cipher cipher = Cipher.getInstance(algorithm, provider);
                            Key encrypter = null;
                            Key decrypter = null;
                            String shortAlg = getShortAlg(algorithm);
                            int padLength = 0;
                            if (shortAlg.equals("RSA")) {
                                KeyPair pair = getKeyPair(algorithm);
                                encrypter = pair.getPublic();
                                decrypter = pair.getPrivate();
                                padLength = getPaddingLength(algorithm, 
                                        baseKey.getEncoded().length, 
                                        256);
                            } else {
                                encrypter = getKey(algorithm);
                                decrypter = encrypter;
                            }
                            AlgorithmParameterSpec spec = getCipherParameterSpec(cipher);
                            cipher = init(cipher, Cipher.WRAP_MODE, encrypter, spec);
                            byte[] wrappedKey = cipher.wrap(baseKey);
                            cipher = init(cipher, Cipher.UNWRAP_MODE, decrypter, spec);
                            Key unwrappedKey = cipher.unwrap(wrappedKey, 
                                    baseKey.getAlgorithm(), 
                                    Cipher.SECRET_KEY);
                            if (padLength != 0) {
                                unwrappedKey = new SecretKeySpec(unwrappedKey.getEncoded(), 
                                        padLength, 
                                        baseKey.getEncoded().length, 
                                        "AES");
                            }
                            baseCipher.init(Cipher.DECRYPT_MODE, 
                                    unwrappedKey, 
                                    baseCipher.getParameters());
                            byte[] output = baseCipher.doFinal(cipherText);
                            sb.append(Arrays.equals(input, output)).append("\n");
                        } catch (Exception e) {
                            e.printStackTrace(new PrintStream(baos));
                            sb.append(baos);
                        }
                    }
                }
                return sb.toString();
            }
        };
        CIPHER_OPERATIONS.add(callable);
        
        // RSA
        callable = new Callable<String>() {
            @Override
            public String call() {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Cipher Operation RSA =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(0)) {
                        if(!isRSA(algorithm)) {
                            continue;
                        }
                        sb.append("Testcase: ")
                                .append(provider)
                                .append(FIELD_SEPARATOR)
                                .append(algorithm)
                                .append(FIELD_SEPARATOR)
                                .append("RSA")
                                .append(" Result: ");
                        ByteArrayOutputStream baos = null;
                        Key baseKey = null;
                        try {
                            baos = new ByteArrayOutputStream();
                            if (baseKey == null) {
                                baseKey = getKey("AES");
                            }
                            Cipher baseCipher = Cipher.getInstance("AES");
                            baseCipher.init(Cipher.ENCRYPT_MODE, baseKey);
                            byte[] input = new byte[256];
                            rd.nextBytes(input);
                            byte[] cipherText = baseCipher.doFinal(input);
                            Cipher cipher = Cipher.getInstance(algorithm, provider);
                            KeyPair pair = getKeyPair(algorithm);
                            AlgorithmParameterSpec spec = getCipherParameterSpec(cipher);
                            cipher = init(cipher, Cipher.WRAP_MODE, pair.getPublic(), spec);
                            byte[] wrappedKey = cipher.wrap(baseKey);
                            cipher = init(cipher, Cipher.UNWRAP_MODE, pair.getPrivate(), spec);
                            Key unwrappedKey = cipher.unwrap(wrappedKey, 
                                    baseKey.getAlgorithm(), 
                                    Cipher.SECRET_KEY);
                            baseCipher.init(Cipher.DECRYPT_MODE, 
                                    unwrappedKey, 
                                    baseCipher.getParameters());
                            byte[] output = baseCipher.doFinal(cipherText);
                            sb.append(Arrays.equals(input, output)).append("\n");
                        } catch (Exception e) {
                            e.printStackTrace(new PrintStream(baos));
                            sb.append(baos);
                        }
                    }
                }
                return sb.toString();
            }
        };
        //CIPHER_OPERATIONS.add(callable);    //TODO: Do we really need a separate op for RSA? Not unless we code something other than just keywrap.
        
        // updateAAD - GCM
        callable = new Callable<String>() {
            @Override
            public String call() {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Cipher Operation GCM updateAAD() =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(0)) {
                        String mode = getMode(algorithm);
                        if(!"GCM".equals(mode)) {
                            continue;
                        }
                        sb.append("Testcase: ")
                                .append(provider)
                                .append(FIELD_SEPARATOR)
                                .append(algorithm);
                        ByteArrayOutputStream baos = null;
                        try {
                            baos = new ByteArrayOutputStream();
                            Cipher cipher = Cipher.getInstance(algorithm, provider);
                            Key key = getKey(algorithm);
                            AlgorithmParameterSpec spec = getCipherParameterSpec(cipher);
                            cipher = init(cipher, Cipher.ENCRYPT_MODE, key, spec);
                            byte[] aad = new byte[64];
                            new SecureRandom().nextBytes(aad);
                            cipher.updateAAD(aad);
                            ByteArrayOutputStream inputBuffer = new ByteArrayOutputStream();
                            ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
                            for (int i = 0; i < 3; i++) {
                                int length = DATA_LENGTHS[(int) (Math.random() * DATA_LENGTHS.length)];
                                byte[] data = new byte[length];
                                rd.nextBytes(data);
                                inputBuffer.write(data);
                                byte[] result = cipher.update(data);
                                if (result != null) {
                                    outputBuffer.write(result);
                                }
                                sb.append(FIELD_SEPARATOR)
                                        .append(length);
                            }
                            byte[] input = inputBuffer.toByteArray();
                            outputBuffer.write(cipher.doFinal());
                            sb.append(" Result: ");
                            cipher = init(cipher, Cipher.DECRYPT_MODE, key, spec);
                            cipher.updateAAD(aad);
                            byte[] cipherText = outputBuffer.toByteArray();
                            outputBuffer.reset();
                            ByteArrayInputStream cipherStream = new ByteArrayInputStream(cipherText);
                            byte[] data = new byte[cipher.getBlockSize() != 0 ? cipher.getBlockSize() : 128];
                            int bytes = 0;
                            while ((bytes = cipherStream.read(data)) > 0) {
                                byte[] result = cipher.update(data, 0, bytes);
                                if (result != null) {
                                    outputBuffer.write(result);
                                }
                            }
                            outputBuffer.write(cipher.doFinal());
                            byte[] output = Arrays.copyOf(outputBuffer.toByteArray(), 
                                    inputBuffer.size());
                            sb.append(Arrays.equals(input, output)).append("\n");

                        } catch (Exception e) {
                            e.printStackTrace(new PrintStream(baos));
                            sb.append(baos);
                        }
                    }
                }
                return sb.toString();
            }
        };
        CIPHER_OPERATIONS.add(callable);
        
    }
    
    private final List<Callable<String>> SIGNATURE_OPERATIONS = new ArrayList<>();
    {
        
        // update - sign - verify
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Sinature Operation update(), sign(), verify() =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(1)) {
                        String pre = "TestCase: " + provider + FIELD_SEPARATOR + algorithm;
                        ByteArrayOutputStream baos = null;
                        try {
                            baos = new ByteArrayOutputStream();
                            Signature signature = Signature.getInstance(algorithm, provider);
                            KeyPair pair = getKeyPair(algorithm);
                            for (int length : DATA_LENGTHS) {
                                if (!isSignatureLengthValid(algorithm, length)) {
                                    continue;
                                }
                                sb.append(pre)
                                        .append(FIELD_SEPARATOR)
                                        .append(length)
                                        .append(" Result: ");
                                byte[] input = new byte[length];
                                rd.nextBytes(input);
                                signature.initSign(pair.getPrivate());
                                signature.setParameter(getSignatureParameterSpec(algorithm));
                                signature.update(input);
                                byte[] signed = signature.sign();
                                signature.initVerify(pair.getPublic());
                                signature.setParameter(getSignatureParameterSpec(algorithm));
                                signature.update(input);
                                sb.append(signature.verify(signed)).append("\n");
                            }
                        } catch (Exception e) {
                            sb.append(pre)
                                    .append(" Result: ");
                            e.printStackTrace(new PrintStream(baos));
                            sb.append(baos);
                        }
                    }
                }
                sb.append("Success\n");
                return sb.toString();
            }
        };
        SIGNATURE_OPERATIONS.add(callable);
        
        // clone
        callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Sinature Operation clone() =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(1)) {
                        String pre = "TestCase: "
                                + provider
                                + FIELD_SEPARATOR
                                + algorithm;
                        ByteArrayOutputStream baos = null;
                        try {
                            baos = new ByteArrayOutputStream();
                            Signature signature = Signature.getInstance(algorithm, provider);
                            KeyPair pair = getKeyPair(algorithm);
                            int length = 0;
                            while (!isSignatureLengthValid(algorithm, length)) {
                                length = DATA_LENGTHS[(int) (Math.random() * DATA_LENGTHS.length)];
                            }
                            sb.append(pre)
                                    .append(FIELD_SEPARATOR)
                                    .append(length)
                                    .append(" Result: ");
                            byte[] input = new byte[length];
                            rd.nextBytes(input);
                            signature.initSign(pair.getPrivate());
                            signature.setParameter(getSignatureParameterSpec(algorithm));
                            signature.update(input);
                            byte[] signed = signature.sign();
                            signature = (Signature) signature.clone(); 
                            signature.initVerify(pair.getPublic());
                            signature.setParameter(getSignatureParameterSpec(algorithm));
                            signature.update(input);
                            sb.append(signature.verify(signed)).append("\n");
                        } catch (CloneNotSupportedException e) {
                            sb.append("NO_SUPPORT").append("\n");
                            continue;
                        } catch (Exception e) {
                            sb.append(pre)
                                    .append(" Result: ");
                            e.printStackTrace(new PrintStream(baos));
                            sb.append(baos);
                        }
                    }
                }
                sb.append("Success\n");
                return sb.toString();
            }
        };
        SIGNATURE_OPERATIONS.add(callable);
        
        // getAlgorithm, getParameters, getProvider
        callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Sinature Operation getAlgorithm(), getProvider(), getParameters() =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(1)) {
                        String pre = "TestCase: "
                                + provider
                                + FIELD_SEPARATOR
                                + algorithm;
                        ByteArrayOutputStream baos = null;
                        try {
                            baos = new ByteArrayOutputStream();
                            Signature signature = Signature.getInstance(algorithm, provider);
                            KeyPair pair = getKeyPair(algorithm);
                            int length = 0;
                            while (!isSignatureLengthValid(algorithm, length)) {
                                length = DATA_LENGTHS[(int) (Math.random() * DATA_LENGTHS.length)];
                            } 
                            sb.append(pre)
                                    .append(FIELD_SEPARATOR)
                                    .append(length)
                                    .append(" Result: ");
                            byte[] input = new byte[length];
                            rd.nextBytes(input);
                            signature.initSign(pair.getPrivate());
                            signature.setParameter(getSignatureParameterSpec(algorithm));
                            signature.update(input);
                            signature.sign();
                            sb.append(provider.getName().equals(signature.getProvider().getName()))
                                    .append(FIELD_SEPARATOR)
                                    .append(algorithm.equals(signature.getAlgorithm()))
                                    .append(FIELD_SEPARATOR)
                                    .append(oneLine(signature.getParameters()))
                                    .append("\n");
                        } catch (Exception e) {
                            sb.append(pre)
                                    .append(" Result: ");
                            e.printStackTrace(new PrintStream(baos));
                            sb.append(baos);
                        }
                    }
                }
                sb.append("Success\n");
                return sb.toString();
            }
        };
        SIGNATURE_OPERATIONS.add(callable);
        
        // Dummy tester for troubleshooting random logic issues
        callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "" + isSignatureLengthValid("NONEwithECDSAinP1363Format", 128);
            }
        };
        //SIGNATURE_OPERATIONS.add(callable);
        
    }
    
    private final List<Callable<String>> DIGEST_OPERATIONS = new ArrayList<>();    
    {
        
        // update - digest
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Digest Operation #0 =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(2)) {
                        MessageDigest md = MessageDigest.getInstance(algorithm, provider);
                        for (int length : DATA_LENGTHS) {
                            sb.append("TestCase: ")
                                    .append(provider)
                                    .append(FIELD_SEPARATOR)
                                    .append(algorithm)
                                    .append(FIELD_SEPARATOR)
                                    .append(length)
                                    .append(" Result: ");
                            byte[] input = new byte[length];
                            rd.nextBytes(input);
                            md.update(input);
                            sb.append(md.digest().length).append("\n");
                            System.out.println(md.digest().length);
                            //sb.append(new String(md.digest())).append("\n");
                        }
                    }
                }
                sb.append("Success\n");
                return sb.toString();
            }
        };
        DIGEST_OPERATIONS.add(callable);
        
        // update - update - digest
        callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Digest Operation #1 =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(2)) {
                        MessageDigest md = MessageDigest.getInstance(algorithm, provider);
                        int length = DATA_LENGTHS[(int) (Math.random() * DATA_LENGTHS.length)];
                        sb.append("TestCase: ")
                                .append(provider)
                                .append(FIELD_SEPARATOR)
                                .append(algorithm)
                                .append(FIELD_SEPARATOR)
                                .append(length)
                                .append(FIELD_SEPARATOR);
                        byte[] input1 = new byte[length];
                        length = DATA_LENGTHS[(int) (Math.random() * DATA_LENGTHS.length)];
                        byte[] input2 = new byte[length];
                        sb.append(length)
                                .append(" Result: ");
                        rd.nextBytes(input1);
                        md.update(input1);
                        rd.nextBytes(input2);
                        md.update(input2);
                        sb.append(md.digest().length).append("\n");
                    }
                }
                sb.append("Success\n");
                return sb.toString();
            }
        };
        DIGEST_OPERATIONS.add(callable);
        
        // update - digest - update - digest
        callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Digest Operation #2 =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(2)) {
                        MessageDigest md = MessageDigest.getInstance(algorithm, provider);
                        int length = DATA_LENGTHS[(int) (Math.random() * DATA_LENGTHS.length)];
                        sb.append("TestCase: ")
                                .append(provider)
                                .append(FIELD_SEPARATOR)
                                .append(algorithm)
                                .append(FIELD_SEPARATOR)
                                .append(length)
                                .append(FIELD_SEPARATOR);
                        byte[] input1 = new byte[length];
                        length = DATA_LENGTHS[(int) (Math.random() * DATA_LENGTHS.length)];
                        byte[] input2 = new byte[length];
                        sb.append(length)
                                .append(" Result: ");
                        rd.nextBytes(input1);
                        md.update(input1);
                        sb.append(md.digest().length).append(FIELD_SEPARATOR);
                        rd.nextBytes(input2);
                        md.update(input2);
                        sb.append(md.digest().length).append("\n");
                    }
                }
                sb.append("Success\n");
                return sb.toString();
            }
        };
        DIGEST_OPERATIONS.add(callable);
        
        // getProvider, getAlgorithm, getDigestLength
        callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Digest Operation #3 =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(2)) {
                        MessageDigest md = MessageDigest.getInstance(algorithm, provider);
                        int length = DATA_LENGTHS[(int) (Math.random() * DATA_LENGTHS.length)];
                        sb.append("TestCase: ")
                                .append(provider)
                                .append(FIELD_SEPARATOR)
                                .append(algorithm)
                                .append(FIELD_SEPARATOR)
                                .append(length)
                                .append(" Result: ");
                        byte[] input = new byte[length];
                        rd.nextBytes(input);
                        md.update(input);
                        sb.append(md.getProvider())
                                .append(FIELD_SEPARATOR)
                                .append(md.getAlgorithm())
                                .append(FIELD_SEPARATOR)
                                .append(md.getDigestLength()).append("\n");
                    }
                }
                sb.append("Success\n");
                return sb.toString();
            }
        };
        DIGEST_OPERATIONS.add(callable);
        
        // clone()
        callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Digest Operation #4 =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(2)) {
                        MessageDigest md = MessageDigest.getInstance(algorithm, provider);
                        int length = DATA_LENGTHS[(int) (Math.random() * DATA_LENGTHS.length)];
                        sb.append("TestCase: ")
                                .append(provider)
                                .append(FIELD_SEPARATOR)
                                .append(algorithm)
                                .append(FIELD_SEPARATOR)
                                .append(length)
                                .append(" Result: ");
                        byte[] input = new byte[length];
                        rd.nextBytes(input);
                        md.update(input);
                        MessageDigest md2 = (MessageDigest) md.clone();
                        byte[] output1 = md.digest();
                        byte[] output2 = md2.digest();
                        sb.append(Arrays.equals(output1, output2)).append("\n");
                    }
                }
                sb.append("Success\n");
                return sb.toString();
            }
        };
        DIGEST_OPERATIONS.add(callable);
        
        // no-update; just digest
        callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder sb = new StringBuilder();
                sb.append("========= Digest Operation #5 =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(2)) {
                        MessageDigest md = MessageDigest.getInstance(algorithm, provider);
                        sb.append("TestCase: ")
                                .append(provider)
                                .append(FIELD_SEPARATOR)
                                .append(algorithm)
                                .append(" Result: ");
                        sb.append(md.digest().length).append("\n");
                    }
                }
                sb.append("Success\n");
                return sb.toString();
            }
        };
        DIGEST_OPERATIONS.add(callable);
        
        // digest - digest
        callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder sb = new StringBuilder();
                Random rd = new Random();
                sb.append("========= Digest Operation #6 =========\n");
                for (Provider provider : PROVIDERS) {
                    for (String algorithm : ALGORITHMS.get(provider).get(2)) {
                        MessageDigest md = MessageDigest.getInstance(algorithm, provider);
                        int length = DATA_LENGTHS[(int) (Math.random() * DATA_LENGTHS.length)];
                        sb.append("TestCase: ")
                                .append(provider)
                                .append(FIELD_SEPARATOR)
                                .append(algorithm)
                                .append(FIELD_SEPARATOR)
                                .append(length)
                                .append(" Result: ");
                        byte[] input = new byte[length];
                        rd.nextBytes(input);
                        md.update(input);
                        sb.append(md.digest().length)
                                .append(FIELD_SEPARATOR)
                                .append(md.digest().length)
                                .append("\n");
                    }
                }
                sb.append("Success\n");
                return sb.toString();
            }
        };
        DIGEST_OPERATIONS.add(callable);
        
    }
    
    public ProviderTest(String[] providers) {
    	System.out.println(providers.length);
        PROVIDERS = new Provider[providers.length];
        for (int i = 0; i < providers.length; i++) {
            PROVIDERS[i] = Security.getProvider(providers[i]);
            System.out.println(Security.getProvider(providers[i]));
        }
        ALGORITHMS = getAlgorithms();
    }
    
    public ProviderTest() {
        this(new String[] {"SunPKCS11-Solaris", "OracleUcrypto"});
    }
    
    private Map<Provider, List<List<String>>> getAlgorithms() {
        Map<Provider, List<List<String>>> map = new HashMap<>();
        for (Provider provider : PROVIDERS) {
            List<String> cipherAlgos = new ArrayList<>();
            List<String> signatureAlgos = new ArrayList<>();
            List<String> digestAlgos = new ArrayList<>();
            List<List<String>> list = new ArrayList<>();
            list.add(cipherAlgos);
            list.add(signatureAlgos);
            list.add(digestAlgos);
            for (Provider.Service service : provider.getServices()) {
                if ("Cipher".equalsIgnoreCase(service.getType())) {
                    cipherAlgos.add(service.getAlgorithm());
                } else if("Signature".equalsIgnoreCase(service.getType())) {
                    signatureAlgos.add(service.getAlgorithm());
                } else if("MessageDigest".equalsIgnoreCase(service.getType())) {
                    digestAlgos.add(service.getAlgorithm());
                }
            }
            map.put(provider, list);
        }
        return map;
    }
    
    private volatile int opId = 0;    
    private int getTask(List<Callable<String>> operations) {
        if (opId >= operations.size()) {
            opId = 0;
        }
        return opId++;
    }
    
    private String getShortAlg(String algorithm) {
        String shortAlg = algorithm;
        int idx = shortAlg.indexOf("/");
        if (idx != -1) {
            shortAlg = algorithm.substring(0, idx);
        }
        idx = shortAlg.indexOf("_");
        if (idx != -1) {
            shortAlg = shortAlg.substring(0, idx);
        }
        idx = shortAlg.indexOf("-");
        if (idx != -1) {
            shortAlg = shortAlg.substring(0, idx);
        }
        shortAlg = shortAlg.replace("Wrap", "");
        shortAlg = shortAlg.replace("Raw", "");
        idx = shortAlg.indexOf("with");
        if (idx != -1) {
            shortAlg = shortAlg.substring(idx + 4);
        }
        idx = shortAlg.indexOf("in");
        if (idx != -1) {
            shortAlg = shortAlg.substring(0, idx);
        }
        if (shortAlg.startsWith("PBE")) {
            shortAlg = "PBE";
        }
        if (shortAlg.startsWith("EC")) {
            shortAlg = "EC";
        }
        return shortAlg;
    }
    
    private int getKeysize(String algorithm) {
        int keysize = -1;
        String shortAlg = getShortAlg(algorithm);
        int idx = algorithm.indexOf("_");
        if (idx != -1) {
            int start = idx + 1;
            idx = algorithm.indexOf("/", start);
            keysize = Integer.parseInt(algorithm.substring(start, 
                    idx != -1 ? idx : algorithm.length()));
        }
        switch (shortAlg) {
            case "AES":
                keysize = keysize == -1 ? 128 : keysize;
                break;
            case "DES":
                keysize = 56;
                break;
            case "DESede":
            case "TripleDES":
                keysize = 168;
                break;
            case "Blowfish":
                keysize = 448;
                break;
            case "RC2":
            case "RC4":
            case "ARCFOUR":
                keysize = keysize == -1 ? 1024 : keysize;
                break;
            case "RSA":
                keysize = 2048;
                break;
            case "DSA":
                keysize = 1024;
                break;
            case "EC":
                keysize = 571;
                break;
        }
        return keysize;
    }
    
    private int getPaddingLength(String algorithm, int length, int blockSize) {
        int padLength = 0;
        if (algorithm.endsWith("/NoPadding")) {
            String mode = getMode(algorithm);
            switch (mode) {
                case "CBC":
                case "ECB":
                    if (blockSize != 0 && length % blockSize != 0) {
                        padLength = blockSize - length % blockSize;
                    }
                    break;
            }
        }
        return padLength;
    }
    
    private static String getMode(String algorithm) {
        String mode = null;
        int idx = algorithm.indexOf("/");
        if (idx != -1) {
            mode = algorithm.substring(idx + 1, algorithm.indexOf("/", idx + 1));
        }
        return mode;
    }
    
    private boolean isSymmetric(String algorithm) {
        String shortAlg = getShortAlg(algorithm);
        return !shortAlg.equals("RSA")
                && !algorithm.contains("Wrap");
    }
    
    private boolean isKeyWrap(Provider provider, String algorithm) {
        if ("SunPKCS11-Solaris".equalsIgnoreCase(provider.getName())) {
            if (algorithm.startsWith("AES") 
                    || algorithm.startsWith("DES")
                    || algorithm.startsWith("Blowfish")) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isRSA(String algorithm) {
        String shortAlg = getShortAlg(algorithm);
        return shortAlg.equals("RSA");
    }
    
    private AlgorithmParameterSpec getCipherParameterSpec(Cipher cipher) 
            throws Exception {
        String algorithm = cipher.getAlgorithm();
        String mode = getMode(algorithm);
        if (mode == null || mode.equals("ECB")) {
            return null;
        }
        int blockSize = cipher.getBlockSize();
        if (blockSize == 0) {
            blockSize = 16;
        }
        byte[] ivBytes = new byte[blockSize];
        new SecureRandom().nextBytes(ivBytes);
        AlgorithmParameterSpec spec = null;
        if (mode.equals("GCM")) {
            spec = new GCMParameterSpec(GCM_TAG_LENGTH, ivBytes);
        } else if (mode.equals("CBC") 
                || mode.equals("CTR") 
                || mode.equals("Blowfish")
                || mode.startsWith("CFB")) {
            spec = new IvParameterSpec(ivBytes);
        } else if (mode.equals("PBE")) {
            spec = new PBEParameterSpec(ivBytes, 3);
        }
        return spec;
    }
    
    private Cipher init(Cipher cipher, int mode, Key key, AlgorithmParameterSpec spec) 
            throws Exception {
        if (mode == Cipher.ENCRYPT_MODE || mode == Cipher.WRAP_MODE) {
            cipher.init(mode, key, spec);
            return cipher;
        }
        String algorithm = cipher.getAlgorithm();
        AlgorithmParameters params = cipher.getParameters();
        cipher = Cipher.getInstance(algorithm);
        if (algorithm.equals("DESedeWrap")) {
            cipher.init(mode, key);
            return cipher;
        }
        cipher.init(mode, key, params);
        return cipher;
    }
    
    private AlgorithmParameterSpec getSignatureParameterSpec(String algorithm) throws Exception {
        AlgorithmParameterSpec spec = null;
        if ("RSASSA-PSS".equals(algorithm)) {
            spec = PSSParameterSpec.DEFAULT;
        }
        return spec;
    }
    
    private boolean isSignatureLengthValid(String algorithm, int length) 
            throws Exception {
        if (algorithm.startsWith("NONE") || algorithm.startsWith("Raw")) {
            if (length == 0) {
                return false;
            }
            String shortAlg = getShortAlg(algorithm);
            if ("DSA".equalsIgnoreCase(shortAlg) && length != 20) {
                return false;
            }
            if ("EC".equalsIgnoreCase(shortAlg) && length > 64) {
                return false;
            }
        }
        return true;
    }
    
    private String oneLine(Object ob) {
        String s = null;
        if (ob != null) {
            s = ob.toString().replaceAll("\n", " ");
        }
        return s;
    }
    
    private KeyPair getKeyPair(String algorithm) throws Exception {
        String shortAlg = getShortAlg(algorithm);
        KeyPair pair = PAIR_MAP.get(shortAlg);
        if (pair != null) {
            return pair;
        }
        int keysize = getKeysize(algorithm);
        KeyPairGenerator generator = KeyPairGenerator.getInstance(shortAlg);
        generator.initialize(keysize);
        pair = generator.generateKeyPair();
        PAIR_MAP.put(shortAlg, pair);
        return pair;
    }
    
    private Key getKey(String algorithm) throws Exception {
        Key key = null;
        String shortAlg = getShortAlg(algorithm);
        if (shortAlg.equals("RSA")) {
            return null;
        }
        int keysize = getKeysize(algorithm);
        String idx = shortAlg + "_" + keysize;
        if (KEY_MAP.get(idx) != null) {
            return KEY_MAP.get(idx);
        }
        if (shortAlg.equals("PBE")) {
            key = getPBEKey(algorithm);
        } else {
            KeyGenerator generator = KeyGenerator.getInstance(shortAlg);
            generator.init(keysize);
            key = generator.generateKey();
        }
        KEY_MAP.put(idx, key);
        return key;
    }
    
    private Key getPBEKey(String algorithm) throws Exception {
        char[] password = "password".toCharArray();
        PBEKeySpec spec = new PBEKeySpec(password);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBE");
        return factory.generateSecret(spec);
    }

    public static void main(String[] args) throws IOException {
        System.out.println(Runtime.getRuntime().availableProcessors());
        NUM_THREADS = Integer.parseInt(args[0]);
        String[] providersList = new String[args.length-1];
        System.arraycopy(args, 1, providersList, 0, args.length-1);
        ProviderTest pt = null;
        if (args != null && args.length != 1) {
            pt = new ProviderTest(providersList);
        } else {
            pt = new ProviderTest();
        }
        pt.test("cipher");
        pt.test("signature");
        pt.test("digest");
    }
    
    private void test(String type) throws IOException {
    	
        List<Callable<String>> operations = null;
        
        switch(type) {
            case "cipher":
                operations = CIPHER_OPERATIONS;
                break;
            case "signature":
                operations = SIGNATURE_OPERATIONS;
                break;
            case "digest":
                operations = DIGEST_OPERATIONS;
                break;
        }
        
        System.out.println("Test Start");
        
        final ExecutorService service = Executors.newFixedThreadPool(NUM_THREADS);
        
        Callable<String> consumer = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Future<String> result = null;
                while (!isDone && results.peek() == null) {
                    sleep(100);
                }
                while (!isDone || results.peek() != null) {
                    result = results.poll();
                    if (result != null) {
                        try {
                            System.out.println(result.get());
                            writeOutputFile(result.get());
                        } catch (Exception e) {
                            System.out.println("Exception");
                            e.printStackTrace();
                        }
                    }
                }
                return "All Done";
            }
        };
        Future<String> endResult = service.submit(consumer);
        
        System.out.println(operations.size());
        isDone = false;
        opId = 0;
        for (int i = 0; i < NUM_TASKS; i++) {
            int opId = getTask(operations);
            Callable<String> callable = operations.get(opId);
            Future<String> result = null;
            try {
             result = service.submit(callable);
            }
            catch(Exception e)
            {
                System.out.println("error");
            }
            while (!results.offer(result)) {
                sleep(100);
            }
        }
        isDone = true;
        
        try {
            System.out.println(endResult.get());
        } catch (Exception e) {
            System.out.println("Consumer Error");
            e.printStackTrace();
        }
        
        service.shutdown();
        
    }
    
    void writeOutputFile(String string) throws IOException {
        String home = System.getProperty("user.home");
        String fileSep = System.getProperty("file.separator");  
        File dire = new File(home+fileSep+"abhishek");
        if(!dire.exists()) {
            dire.mkdir();
        }
        File file=new File(home+fileSep+"abhishek"+fileSep+"output.txt");
        FileWriter write=new FileWriter(file,true);
        write.append(string);
        write.close();
        
    }

}