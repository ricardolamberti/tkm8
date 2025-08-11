package pss.common.terminals.messages.requires;

/**
 * Description:
 *
 * @author Ricardo Lamberti, Iván Rubín
 *
 */
import pss.common.terminals.messages.answer.Answer;
import pss.core.services.fields.JObjRecords;
import pss.core.tools.collections.JIterator;

public class JEvents extends JObjRecords {

	// private JList oEvents;

	public JEvents() {
	}

	// public void add( Answer zEvent ) {
	// if (this.oEvents==null) this.oEvents=JCollectionFactory.createList();
	// this.oEvents.addElement( zEvent );
	// }

	/**
	 * Answers the first ocurrence of zEvent (through matches) in this eventlist
	 */
	public Answer matches(Answer newEvent) throws Exception {
		JIterator iter=this.getValueIterator();
		while (iter.hasMoreElements()) {
			Answer event=(Answer) iter.nextElement();
			if (!newEvent.is(event.getClass())) continue;
			if (event.matches(newEvent)) {
				return newEvent;
			}
		}
		return null;
	}

	/**
	 * answers the first ocurrence of zEvent's class on this event list
	 */
	public Answer has(Class zEventClass) throws Exception {
		JIterator iter=this.getValueIterator();
		while (iter.hasMoreElements()) {
			Answer event=(Answer) iter.nextElement();
			if (!event.is(zEventClass)) continue;
			return event;
		}
		return null;
	}

}
