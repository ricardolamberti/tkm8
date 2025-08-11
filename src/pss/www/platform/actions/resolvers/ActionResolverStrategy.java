package pss.www.platform.actions.resolvers;

import pss.core.win.submits.JAct;
import pss.www.platform.actions.results.JWebActionResult;

/**
 * Strategy used by {@link JDoPssActionResolver} to process a particular
 * {@link JAct}. Each implementation decides whether it supports the action and
 * how to handle it, returning a {@link JWebActionResult} or delegating back to
 * the resolver.
 */
public interface ActionResolverStrategy {
    /**
     * Determines if this strategy can handle the given submit.
     */
    boolean supports(JDoPssActionResolver context, JAct submit) throws Exception;

    /**
     * Performs the logic for the given submit when supported.
     */
    JWebActionResult handle(JDoPssActionResolver context, JAct submit) throws Throwable;
}
