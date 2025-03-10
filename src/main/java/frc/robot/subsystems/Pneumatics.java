package frc.robot.subsystems;

    import edu.wpi.first.wpilibj.DoubleSolenoid;
    import edu.wpi.first.wpilibj.Compressor;
    import edu.wpi.first.wpilibj.PneumaticsModuleType;
    import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
    
    public class Pneumatics {
    
        // Define Pneumatics Control Module (PCM) and Compressor
        private static final int PCM_MODULE_ID = 1; // Replace with your PCM module ID
        private static final int COMPRESSOR_ID = 1; // Replace with your compressor ID
    
        // Define Solenoid Channels
        private static final int SOLENOID_FORWARD_CHANNEL = 0; // Replace with your solenoid forward channel
        private static final int SOLENOID_REVERSE_CHANNEL = 1; // Replace with your solenoid reverse channel
                    
                        // Define Compressor
                        private Compressor m_compressor = new Compressor(COMPRESSOR_ID, PneumaticsModuleType.CTREPCM);
                    
                        // Define Double Solenoid
                        private DoubleSolenoid m_solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, SOLENOID_FORWARD_CHANNEL, SOLENOID_REVERSE_CHANNEL);
                        
                        // Constructor
                        public Pneumatics() {
                            // Initialize Compressor
                            m_compressor.enableDigital();
                        }
                    
                        // Method to extend the piston
                        public void extendPiston() {
                            m_solenoid.set(Value.kForward);
                        }
                    
                        // Method to retract the piston
                        public void retractPiston() {
                            m_solenoid.set(Value.kReverse);
                        }
                    
                        // Method to turn on the compressor
                        public void enableCompressor() {
                            m_compressor.enableDigital();
                        }
                    
                        // Method to turn off the compressor
                        public void disableCompressor() {
                            m_compressor.disable();                                      
 
 //if (m_driverController.getRawButton(kDoubleSolenoid)) {
 //   m_doubleSolenoid.toggle();
 // }
}    
}