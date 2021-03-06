/*
 This groovy scripts is to execute a shell script on a linux machine, from a Windows Environment.
 Dependencies: jsch-0.1.55.jar (available in the dependencies folder.)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session; 


JSch jsch = new JSch();

        Session session;
        try {

            // Open a Session to remote SSH server and Connect.
            // Set User and IP of the remote host and SSH port.
            session = jsch.getSession("ubuntu", "10.127.117.235", 22);
            // When we do SSH to a remote host for the 1st time or if key at the remote host 
            // changes, we will be prompted to confirm the authenticity of remote host. 
            // This check feature is controlled by StrictHostKeyChecking ssh parameter. 
            // By default StrictHostKeyChecking  is set to yes as a security measure.
            session.setConfig("StrictHostKeyChecking", "no");
            //Set password
            session.setPassword("12345");
            session.connect();

            // create the execution channel over the session
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            // Set the command to execute on the channel and execute the command
            channelExec.setCommand("sh /home/ubuntu/test.sh");
            channelExec.connect();

            // Get an InputStream from this channel and read messages, generated 
            // by the executing command, from the remote side.
            InputStream inw = channelExec.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inw));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }

            // Command execution completed here.

            // Retrieve the exit status of the executed command
            int exitStatus = channelExec.getExitStatus();
            if (exitStatus > 0) {
                log.info("Remote script exec error! " + exitStatus);
            }
            //Disconnect the Session
            session.disconnect();
        } catch (Exception ex) {
            log.info "Exception in the inside block: " + ex
        } catch (Exception e) {
            log.info "Exception in the inside block: " + e
        }
