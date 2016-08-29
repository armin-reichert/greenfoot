import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

/**
 * Plays MIDI files.
 * 
 */
public class Midi
{
	private final String midiFile;
	private Sequencer sequencer;

	public Midi(String midiFile) {
		this.midiFile = midiFile;
	}

	public void load() {
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			sequencer.setSequence(getClass().getResourceAsStream("sounds/" + midiFile));
			sequencer.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		if (!sequencer.isRunning()) {
			sequencer.start();
		}
	}

	public void stop() {
		if (sequencer.isRunning()) {
			sequencer.stop();
		}
	}

	public void close() {
		if (sequencer.isOpen()) {
			sequencer.close();
		}
	}
}
