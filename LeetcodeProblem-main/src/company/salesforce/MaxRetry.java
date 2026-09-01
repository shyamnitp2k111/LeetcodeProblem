package company.salesforce;
/*

Problem

You are given:
An array timestamps[] representing request times.
An integer windowSize.

Find the maximum number of requests that occur within any continuous time window of length windowSize.

A request is included in a window if:

startTime <= timestamp <= startTime + windowSize

The timestamps are not sorted.

Example
timestamps = [1, 3, 7, 5]
windowSize = 4

After sorting:

[1, 3, 5, 7]

Possible windows:

[1,5] -> contains 1,3,5 -> 3 requests
[3,7] -> contains 3,5,7 -> 3 requests

Maximum requests in any window = 3

Output:

3


Another Example :

timestamps = [2, 2, 10, 12]
windowSize = 0

Window:

[2,2]

Contains both requests at time 2.

Output:

2
*/

import java.util.Arrays;

public class MaxRetry {

    public static int maxRequestsWithinWindow(int[] timestamps, int windowSize) {

        Arrays.sort(timestamps);

        int left = 0;
        int maxRequests = 0;

        for (int right = 0; right < timestamps.length; right++) {

            while (timestamps[right] - timestamps[left] > windowSize) {
                left++;
            }

            maxRequests = Math.max(maxRequests, right - left + 1);
        }

        return maxRequests;
    }

    public static void main(String[] args) {

        int[] timestamps = {1, 3, 7, 5};
        int windowSize = 4;

        System.out.println(maxRequestsWithinWindow(timestamps, windowSize));
    }
}
