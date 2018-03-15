package com.elasor.elasorgank.bean;

import java.util.List;

/**
 * @author Elasor
 */
public class CommonBean {
    private boolean error;
    private List<CommonListBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<CommonListBean> getResults() {
        return results;
    }

    public void setResults(List<CommonListBean> results) {
        this.results = results;
    }
}
