package fa.dfa;

import fa.State;
import java.util.HashMap;

/**
 * Represents a single state within a DFA.
 * Extends the abstract State class and adds functionality for 
 * managing transitions to other DFAStates based on input symbols.
 * * @author Jonathan Boozel and Tony Bowen
 */
public class DFAState extends State {
    private HashMap<Character, DFAState> transitions;
    
    /**
     * Constructor for DFAState.
     * @param name The unique label assigned to this state
     */
    public DFAState(String name)
    {
        super(name);
        this.transitions = new HashMap<Character, DFAState>();
    }

    /**
     * Adds a transition from this state to another state for a specific symbol.
     * @param symbol The input character that triggers the transition.
     * @param toState The destination DFAState.
     */
    public void addTransition(char symbol, DFAState toState)
    {
        transitions.put(symbol, toState);
    }

    /**
     * Retrieves the destination state for a given input symbol.
     * @param symbol The input character to look up.
     * @return The destination DFAState, or null if no transition is defined.
     */
    public DFAState getTransition(char symbol)
    {
        return transitions.get(symbol);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        
        // If it's an instance of DFAState, then compare the states via their names
        if (obj instanceof DFAState)
        {
            DFAState other = (DFAState) obj;
            return this.getName().equals(other.getName());
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return this.getName().hashCode();
    }
}