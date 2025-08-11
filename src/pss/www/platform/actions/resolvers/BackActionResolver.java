package pss.www.platform.actions.resolvers;

import pss.core.win.submits.JAct;
import pss.core.win.submits.JActBack;
import pss.www.platform.actions.results.JWebActionResult;

/**
 * Deals with navigation actions that request to go back in the window history.
 * It can optionally submit the previous action again depending on context.
 */
public class BackActionResolver implements ActionResolverStrategy {
    @Override
    public boolean supports(JDoPssActionResolver context, JAct submit) {
        return submit.getClass().isAssignableFrom(JActBack.class);
    }

    @Override
    public JWebActionResult handle(JDoPssActionResolver context, JAct submit) throws Throwable {
        JAct backSubmit = context.getBackAct((JActBack) submit);
        if (context.hasToSubmit(backSubmit)) {
            return context.processSubmit(backSubmit);
        }
        context.assignTarget(backSubmit);
        return context.goOn();
    }
}
