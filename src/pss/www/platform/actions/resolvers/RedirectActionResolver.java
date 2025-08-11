package pss.www.platform.actions.resolvers;

import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFileGenerate;
import pss.www.platform.actions.results.JWebActionResult;

/**
 * Handles {@link JAct} instances that generate files, producing a redirect
 * response so the file can be downloaded by the client.
 */
public class RedirectActionResolver implements ActionResolverStrategy {
    @Override
    public boolean supports(JDoPssActionResolver context, JAct submit) throws Exception {
        return submit.getFinalSubmit() instanceof JActFileGenerate;
    }

    @Override
    public JWebActionResult handle(JDoPssActionResolver context, JAct submit) throws Throwable {
        return context.processRedirect(submit);
    }
}
