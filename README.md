Acoustic Communication
========
This is the demo for Acoustic communication (as a potential alternative of NFC)

Basic princinple:
The current implementation is based on Frequency Shift Keying, every 2-bit is mapped to one frequency 
among 4 freq total and the corresponding waveform of 441 bits long. A marker byte 10 is used for indicating
the start of the UID. A CRC byte is attached at the end of the byte sequence for error correction. 
Sampling frequency if 44KHz The decoder used FFT. 

As an example, 7-byte UID takes roughly (7+2)*8/2*441/44Khz = 360ms to transmit. 

Encode： Generator.java
Decode: REceiver.java


By xuq
Email: xuq007@gmail.com

Already implemented:

(1) Can identify 7-byte UID with latency < 5 sec 

(2) Detection range < 10cm (even longer also works, can be controlled by speaker volume bar)

(3) a degree of tolerance to background noise (human conversation, music, …)

(4) robustness to phone orientation

(5) A listening background task

(6) CRC32 is included.
