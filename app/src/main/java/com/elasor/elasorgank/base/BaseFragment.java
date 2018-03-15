package com.elasor.elasorgank.base;

import com.elasor.elasorgank.interfaces.IMark;
import com.lify.elasor.base.ElasorLazyFragment;

/**
 * @author Elasor
 */
public abstract class BaseFragment extends ElasorLazyFragment implements IMark{

    private String mMarkIdentifier = "";

    public void setMarkIdentifier(String markIdentifier){
        this.mMarkIdentifier = markIdentifier;
    }

    @Override
    public String mark() {
        return mMarkIdentifier;
    }
}
