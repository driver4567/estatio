package org.estatio.capex.dom.documents.categorisation.transitions;

import java.util.List;

import org.apache.isis.applib.annotation.Mixin;

import org.incode.module.document.dom.impl.docs.Document;

import org.estatio.capex.dom.documents.categorisation.IncomingDocumentCategorisationState;
import org.estatio.capex.dom.documents.categorisation.IncomingDocumentCategorisationStateTransition;
import org.estatio.capex.dom.documents.categorisation.IncomingDocumentCategorisationStateTransitionType;
import org.estatio.capex.dom.state.DomainObject_transitionsAbstract;
import org.estatio.dom.invoice.DocumentTypeData;

@Mixin(method = "coll")
public class Document_categorisationTransitions
        extends DomainObject_transitionsAbstract<
                    Document,
                    IncomingDocumentCategorisationStateTransition,
                    IncomingDocumentCategorisationStateTransitionType,
                    IncomingDocumentCategorisationState> {


    public Document_categorisationTransitions(final Document document) {
        super(document, IncomingDocumentCategorisationStateTransition.class);
    }

    // necessary because Isis' metamodel unable to infer return type from generic method
    @Override
    public List<IncomingDocumentCategorisationStateTransition> coll() {
        return super.coll();
    }


    public boolean hideColl() {
        return !DocumentTypeData.hasIncomingType(domainObject);
    }

}
