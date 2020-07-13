// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.Collection;
import java.util.*;

public final class FindMeetingQuery {
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        Collection<String> attendees = request.getAttendees();
        Collection<String> optional = request.getOptionalAttendees();

        ArrayList<String> both = new ArrayList<String>();

        for(String attendee: attendees){
            both.add(attendee);
        }
        for(String option: optional){
            both.add(option);
        } 

        Collection<TimeRange> optionalSol=queryHelper(both,events,request);
        if(optionalSol.size()!=0){
            return optionalSol;
        }
        else{
            return queryHelper(attendees,events,request);
        }

    }

    public boolean isRelevant(Event event, Collection<String> attendees){
        for(String attendee : event.getAttendees()){
            if (attendees.contains(attendee)){
                return true;
            }
        }
        return false;
    }

    public Collection<TimeRange> queryHelper(Collection<String> attendees, Collection<Event> events, MeetingRequest request){
        ArrayList<Integer> importantTimes = new ArrayList<Integer>();
        HashMap<Integer,Integer> eventCheckVals= new HashMap<Integer,Integer>();
        System.out.println("STARTING________");
        for(Event event : events){
            System.out.println("Time Range:" + event.getWhen().start() + "-" + event.getWhen().end());
            if (isRelevant(event,attendees)){
                int start = event.getWhen().start();
                int end = event.getWhen().end();
                importantTimes.add(start);
                importantTimes.add(end);
                if (eventCheckVals.containsKey(start)){
                    eventCheckVals.put(start,eventCheckVals.get(start)+1);
                }
                else{
                    eventCheckVals.put(start,1);
                }

                if (eventCheckVals.containsKey(end)){
                    eventCheckVals.put(end,eventCheckVals.get(end)-1);
                }
                else{
                    eventCheckVals.put(end,-1);
                }
            }
 
        }
        //importantTimes.add(TimeRange.END_OF_DAY);
        Collections.sort(importantTimes);

        Collection<TimeRange> sol = new ArrayList<>();
        int counter=0;
        int last_time=0;

        if(request.getDuration()>=1440){
            return sol;
        }

        if (importantTimes.size()==0){
            sol.add(TimeRange.WHOLE_DAY);
            return sol;
        }

        for(int time: importantTimes){

            if (counter==0 && counter + eventCheckVals.get(time)>=0 && time-last_time >= request.getDuration()){
                TimeRange timeRangeToAdd = TimeRange.fromStartDuration(last_time, time-last_time);
                sol.add(timeRangeToAdd);
            }
            last_time=time;
            counter += eventCheckVals.get(time);

        }

        if (TimeRange.END_OF_DAY-last_time >= request.getDuration()){
            TimeRange timeRangeToAdd = TimeRange.fromStartEnd(last_time,TimeRange.END_OF_DAY,true);
            sol.add(timeRangeToAdd);
        }

        return sol;


    }
}
