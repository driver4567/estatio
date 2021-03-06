package org.estatio.module.budget.dom;

import java.math.BigDecimal;

public interface Distributable {

    BigDecimal getSourceValue();
    BigDecimal getValue();
    void setValue(BigDecimal value);

}
