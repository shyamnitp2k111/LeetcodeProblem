package companyinterview.salesforce;

/*
A string must be transferred using a custom network protocol.
The protocol processes the string as follows:

   • Each pair of characters in the string is processed together.
   • If any pair contains matching characters (e.g., "aa"), it requires an additional sameTime seconds.
   • The string can be split into substrings before transmission, with each partition adding partition Time seconds.

Calculate the minimum possible total extra time required, which is the sum of:

• Time for processing pairs with matching characters.
• Time for creating partitions.
    Example s= "abcabcc"
    sameTime = 1
    partitionTime = 4

Two optimal approaches are:

1. Split into ["abc", "abcc"]:

• "abc" has no matching pairs: O seconds
• One partition: 4 seconds
• "abcc" has one "cc" pair: 1 second
• Total: 0 + 4 + 1 = 5 seconds


2. Keep as [abcabcc"]:

• 5 matching pairs ("aa", "bb", "CC", "C", "C"): 5 seconds
• Total: 5 seconds

The minimum possible extra time is 5 seconds.
Function Description
Complete the function getMinimumTime in the editor with the following parameters:
strings: the string to transfer
int sameTime: the extra time taken to process a pair of matching characters
int partitionTime: the extra time to partition a string
Returns
the network
int: the minimum possible total extra time to transfer the string s over
Constraints
• 1 ≤ length of s ≤ 2500
• 1 ≤ sameTime, partitionTime ≤ 105

*/
public class Network {

    public static long getMinimumTime(String s, int sameTime, int partitionTime) {

        long total = 0;

        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                total += Math.min(sameTime, partitionTime);
            }
        }
        return total;
    }

    public static void main(String[] args) {

        System.out.println(
                getMinimumTime("aabbcc", 5, 2)
        );
    }
}
