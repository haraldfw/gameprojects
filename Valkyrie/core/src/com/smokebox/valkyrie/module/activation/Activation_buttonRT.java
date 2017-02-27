package com.smokebox.valkyrie.module.activation;

import com.smokebox.valkyrie.module.input.InputModule;

public class Activation_buttonRT implements ActivationModule {

    InputModule ie;

    public Activation_buttonRT(InputModule ie) {
        this.ie = ie;
    }

    @Override
    public boolean activate() {
        return ie.rt();
    }
}
