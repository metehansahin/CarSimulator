package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a log of car simulator events
// We use the Singleton Design Pattern to ensure that there is only
// one EventLog in the system and that the system has global access
// to the single instance of the EventLog.
// This class references code from these repos:
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
public class EventLog implements Iterable<Event> {
    // the only EventLog in the system (Singleton Design Pattern)
    private static EventLog theLog;
    private static Collection<Event> events;

    // EFFECTS: Constructs an ArrayList of events
    //          Prevents external construction (Singleton Design Pattern)
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // EFFECTS: gets instance of EventLog
    //          if it doesn't exist:
    //              creates it
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    // EFFECTS: adds an event to the event log
    public void logEvent(Event e) {
        events.add(e);
    }

    // EFFECTS: clears the event log and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
