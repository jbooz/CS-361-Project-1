package fa.dfa;

import java.util.Set;
import java.util.LinkedHashSet;

import fa.State;

/**
 * Represents a Deterministic Finite Automaton (DFA).
 * This class implements the DFAInterface and manages the states, 
 * alphabet, and transitions that define the automaton's behavior.
 * * @author Jonathan Boozel and Tony Bowen
 */
public class DFA implements DFAInterface {
    private LinkedHashSet<DFAState> stateSet = new LinkedHashSet<DFAState>();
    private LinkedHashSet<Character> alphabet = new LinkedHashSet<Character>();
    private DFAState initialState;
    private LinkedHashSet<DFAState> finalStateSet = new LinkedHashSet<DFAState>();

    @Override
    public boolean addState(String name) {
        DFAState newState = new DFAState(name);
        return stateSet.add(newState);
    }

    @Override
    public boolean setFinal(String name) {
        // Set the state to be a final state if it exists
        State s = getState(name);
        if (s != null)
        {
            finalStateSet.add((DFAState) s);
            return true;
        }
        return false;
    }

    @Override
    public boolean setStart(String name) {
        // Set the state to be the initial state if it exists
        State s = getState(name);
        if (s != null)
        {
            initialState = (DFAState) s;
            return true;
        }
        return false;
    }

    @Override
    public void addSigma(char symbol) {
        // Add the character if the alphabet doesn't already have it
        if (!(alphabet.contains(symbol)))
        {
            alphabet.add(symbol);
        }
    }

    @Override
    public boolean accepts(String s) {
        DFAState curr = initialState;

        // If epsilon, check that the initial state is also a final state
        if (s.equals("e"))
        {
            return finalStateSet.contains(curr);
        }

        // Go through the characters in the string then check that the result is a final state
        for (int i = 0; i < s.length(); i++)
        {
            char symbol = s.charAt(i);
            curr = curr.getTransition(symbol);

            if (curr == null)
            {
                return false;
            }
        }

        return finalStateSet.contains(curr);
    }

    @Override
    public Set<Character> getSigma() {
        LinkedHashSet<Character> alphabetCopy = new LinkedHashSet<Character>();
        alphabetCopy.addAll(alphabet);
        return alphabetCopy;
    }

    @Override
    public State getState(String name) {
        // Loop through the set of all states to find the state
        for (DFAState state : stateSet)
        {
            if (state.getName().equals(name))
            {
                return state;
            }
        }
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        DFAState testState = new DFAState(name);
        return finalStateSet.contains(testState);
    }

    @Override
    public boolean isStart(String name) {
        return initialState != null && initialState.getName().equals(name);
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        State from = getState(fromState);
        State to = getState(toState);
        
        // If the states exist, create a transition for them
        if (from != null && to != null && alphabet.contains(onSymb))
        {
            ((DFAState) from).addTransition(onSymb, (DFAState) to);
            return true;
        }
        return false;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        DFA copy = new DFA();

        // Copy over the alphabet of characters
        for (char s : this.alphabet)
        {
            copy.addSigma(s);
        }

        // Copy over start and final states
        for (DFAState state : this.stateSet)
        {
            String name = state.getName();
            copy.addState(name);

            if (this.isStart(name))
            {
                copy.setStart(name);
            }
            if (this.isFinal(name))
            {
                copy.setFinal(name);
            }
        }

        // Iterate through the characters to reverse the order of the transitions
        for (DFAState origin : this.stateSet)
        {
            for (char s : this.alphabet)
            {
                DFAState destination = origin.getTransition(s);
                if (destination != null)
                {
                    char targetSymb = s;

                    if (s == symb1)
                    {
                        targetSymb = symb2;
                    }
                    else if (s == symb2)
                    {
                        targetSymb = symb1;
                    }

                    copy.addTransition(origin.getName(), destination.getName(), targetSymb);
                }
            }
        }

        return copy;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        // Represent Q, the set of all states
        sb.append("Q = { ");
        for (DFAState state : stateSet)
        {
            sb.append(state.getName()).append(" ");
        }
        sb.append("}\n");

        // Represent Sigma, the alphabet of all characters
        sb.append("Sigma = { ");
        for (Character c : alphabet)
        {
            sb.append(c).append(" ");
        }
        sb.append("}\n");

        // Represent delta, the table of transitions
        sb.append("delta =\n\t\t");
        for (Character c : alphabet)
        {
            sb.append(c).append("\t");
        }
        sb.append("\n");

        // Loop through to associate each state accordingly
        for (DFAState state : stateSet)
        {
            sb.append("\t").append(state.getName()).append("\t");
            for (Character c: alphabet)
            {
                DFAState destination = state.getTransition(c);
                if (destination != null)
                {
                    sb.append(destination.getName()).append("\t");
                }
                else
                {
                    sb.append("").append("\t");
                }
            }
            sb.append("\n");
        }

        // Represent q0, the initial state
        sb.append("q0 = ").append(initialState.getName()).append("\n");

        // Represent F, the set of final states
        sb.append("F = { ");
        for (DFAState state : finalStateSet)
        {
            sb.append(state.getName()).append(" ");
        }
        sb.append("}\n");

        return sb.toString();
    }
}