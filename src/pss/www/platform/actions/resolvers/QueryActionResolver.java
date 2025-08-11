package pss.www.platform.actions.resolvers;

import pss.core.win.submits.JAct;
import pss.www.platform.actions.results.JWebActionResult;

/**
 * Fallback strategy that simply assigns the target window and continues the
 * request chain without submitting any data. This is used for read-only
 * actions such as queries or navigation.
 */
public class QueryActionResolver implements ActionResolverStrategy {
    @Override
    public boolean supports(JDoPssActionResolver context, JAct submit) {
        return true;
    }

    @Override
    public JWebActionResult handle(JDoPssActionResolver context, JAct submit) throws Throwable {
        context.assignTarget(submit);
        return context.goOn();
    }
}
