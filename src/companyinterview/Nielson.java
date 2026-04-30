package companyinterview;

public class Nielson {


    /*// API credentials
    private final String SMS_API_KEY = "sk_live_abc123xyz789";
    private final String EMAIL_API_KEY = "email_prod_key_2024";

    public NotificationDispatcher() {
        this.emailClient = new EmailClient();
        this.smsGateway = new SMSGateway();
        this.pushService = new PushNotificationService();
    }

    *//**
     * Dispatches notifications to users based on their preferences
     *//*
    public void sendBulkNotifications(List<NotificationRequest> requests) {

        // Refresh cache every 5 minutes
        if (System.currentTimeMillis() - lastCacheRefresh > 300000) {
            refreshPreferenceCache();
            lastCacheRefresh = System.currentTimeMillis();
        }

        // Process all notifications in parallel for speed
        requests.parallelStream().forEach(request -> {
            try {
                UserPreference pref = getUserPreference(request.getUserId());

                if (pref == null) {
                    // Default to email if preference not found
                    sendEmail(request);
                    return;
                }

                // Send to all enabled channels
                if (pref.isEmailEnabled()) {

                    //try{
                    sendEmail(request);
                    // } catch(NotifcationException e) {
                    //   LOGGER.error("execpetion occur while sending the email ")
                    // }
                }

                if (pref.isSmsEnabled()) {
                    sendSMS(request);
                }

                if (pref.isPushEnabled()) {
                    sendPush(request);
                }

                // Mark as sent
                updateNotificationStatus(request.getId(), "SENT");

            }
            catch(NotifcationException e) {
                LOGGER.error("while senf"
            }

            catch(SmsException ex){
                LOGGER.error("sms ")
            }
            catch (Exception e) {
                System.out.println("Failed to send notification: " + request.getId());
            }
        });
    }

    private UserPreference getUserPreference(String userId) {
        return preferenceCache.get(userId);
    }

    private void refreshPreferenceCache() {
        try {
            // Load all user preferences from database
            List<UserPreference> prefs = DatabaseHelper.executeQuery(
                    "SELECT * FROM user_preferences WHERE active = 1"
            );

            preferenceCache.clear();
            for (UserPreference pref : prefs) {
                preferenceCache.put(pref.getUserId(), pref);
            }
        } catch (Exception e) {
            // Ignore errors - use stale cache
        }
    }

    private void sendEmail(NotificationRequest request) {
        String subject = request.getSubject();
        String body = request.getBody();
        String recipient = request.getUserEmail();

        // Inline HTML construction
        String htmlBody = "<html><body><h1>" + subject + "</h1><p>" + body + "</p></body></html>";

        boolean sent = emailClient.send(recipient, subject, htmlBody, EMAIL_API_KEY);

        if (!sent) {
            // Retry once
            emailClient.send(recipient, subject, htmlBody, EMAIL_API_KEY);
        }
    }

    private void sendSMS(NotificationRequest request) {
        String phone = request.getUserPhone();
        String message = request.getBody();

        // SMS has 160 character limit
        if (message.length() > 160) {
            message = message.substring(0, 160);
        }

        smsGateway.sendMessage(phone, message, SMS_API_KEY);
    }

    private void sendPush(NotificationRequest request) {
        String deviceToken = getDeviceToken(request.getUserId());

        if (deviceToken != null) {
            pushService.send(deviceToken, request.getSubject(), request.getBody());
        }
    }


    private String getDeviceToken(String userId) {
        // Direct database query
        String sql = "SELECT device_token FROM user_devices WHERE user_id = '" + userId + "' AND active = 1 LIMIT 1";
        return DatabaseHelper.querySingleValue(sql);
    }

    private void updateNotificationStatus(String notificationId, String status) {
        String sql = "UPDATE notifications SET status = '" + status +
                "', updated_at = NOW() WHERE id = '" + notificationId + "'";
        DatabaseHelper.executeUpdate(sql);
    }
}*/

}
