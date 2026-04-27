package interval;

import java.util.List;

class Interval {
    public int start, end;
    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}


class Solution {
    public boolean canAttendMeetings(List<Interval> intervals) {

        intervals.sort((interval1, interval2) -> interval1.start - interval2.start);

        boolean answer = true;

        int prev = 0;

        for(int index = 1; index < intervals.size(); index++) {

            if(intervals.get(prev).end > intervals.get(index).start && intervals.get(prev).start < intervals.get(index).start) {
                answer = false;
                break;
            }
            prev = index;
        }

        return answer;
    }
}
