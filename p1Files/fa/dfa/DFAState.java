package fa.dfa;

import fa.State;
import java.util.HashMap;

public class DFAState extends State {
    private HashMap<Character, DFAState> transitions;
    
    public DFAState(String name)
    {
        super(name);
        this.transitions = new HashMap<Character, DFAState>();
    }

    public void addTransition(char symbol, DFAState toState)
    {
        transitions.put(symbol, toState);
    }

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