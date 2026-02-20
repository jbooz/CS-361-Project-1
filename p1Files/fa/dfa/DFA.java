package fa.dfa;

import java.util.Set;
import java.util.LinkedHashSet;

import fa.State;

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
        DFAState newFinal = new DFAState(name);
        if (stateSet.contains(newFinal))
        {
            finalStateSet.add(newFinal);
            return true;
        }
        return false;
    }

    @Override
    public boolean setStart(String name) {
        DFAState newStart = new DFAState(name);
        if (stateSet.contains(newStart))
        {
            initialState = newStart;
            return true;
        }
        return false;
    }

    @Override
    public void addSigma(char symbol) {
        if (!(alphabet.contains(symbol)))
        {
            alphabet.add(symbol);
        }
    }

    @Override
    public boolean accepts(String s) {
        DFAState curr = initialState;

        if (s.equals("e"))
        {
            return finalStateSet.contains(curr);
        }

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
        DFAState origin = (DFAState) getState(fromState);
        DFAState destination = (DFAState) getState(toState);
        if (origin == null || destination == null || !alphabet.contains(onSymb))
        {
            return false;
        }

        origin.addTransition(onSymb, destination);
        return true;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        DFA copy = new DFA();

        for (char s : this.alphabet)
        {
            copy.addSigma(s);
        }

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

        sb.append("Q = { ");
        for (DFAState state : stateSet)
        {
            sb.append(state.getName()).append(" ");
        }
        sb.append("}\n");

        sb.append("Sigma = { ");
        for (Character c : alphabet)
        {
            sb.append(c).append(" ");
        }
        sb.append("}\n");

        sb.append("delta =\n\t\t");
        for (Character c : alphabet)
        {
            sb.append(c).append("\t");
        }
        sb.append("\n");

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

        sb.append("q0 = ").append(initialState.getName()).append("\n");

        sb.append("F = { ");
        for (DFAState state : finalStateSet)
        {
            sb.append(state.getName()).append(" ");
        }
        sb.append("}\n");

        return sb.toString();
    }
}