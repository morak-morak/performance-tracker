package com.morak.performancetracker.description;

import com.morak.performancetracker.ContextType;
import com.morak.performancetracker.context.Root;

public interface Descriptor {

    void describe(Root root, ContextType contextType);
}
