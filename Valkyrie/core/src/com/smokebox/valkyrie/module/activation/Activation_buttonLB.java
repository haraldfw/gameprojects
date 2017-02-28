package com.smokebox.valkyrie.module.activation;

import com.smokebox.valkyrie.module.input.InputModule;

public class Activation_buttonLB implements ActivationModule {

    InputModule ie;

    public Activation_buttonLB(InputModule ie) {
        this.ie = ie;
    }

    @Override
    public boolean activate() {
        return ie.lb();
    }
}
