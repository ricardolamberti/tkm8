package pss.www.platform.actions.resolvers;

import pss.core.win.submits.JAct;
import pss.www.platform.actions.results.JWebActionResult;

/**
 * Resolves generic submit actions. When the context indicates that the submit
 * should be performed, the resolver invokes {@link JDoPssActionResolver#processSubmit(JAct)}.
 */
public class SubmitActionResolver implements ActionResolverStrategy {
    @Override
    public boolean supports(JDoPssActionResolver context, JAct submit) throws Exception {
        return context.hasToSubmit(submit);
    }

    @Override
    public JWebActionResult handle(JDoPssActionResolver context, JAct submit) throws Throwable {
        return context.processSubmit(submit);
    }
}
