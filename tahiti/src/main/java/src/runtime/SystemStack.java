package src.runtime;

import com.google.common.base.Preconditions;
import src.ShuntingYard;


import java.util.ArrayDeque;
import java.util.Deque;

public class SystemStack {

    private final Deque<ShuntingYard> stacks = new ArrayDeque<>();

    public void create() {

        stacks.push(new ShuntingYard());
    }

    public ShuntingYard current() {

        Preconditions.checkState(stacks.size() > 0);

        return stacks.peek();
    }

    public ShuntingYard close() {

        Preconditions.checkState(stacks.size() > 0);

        return stacks.pop();
    }
}
