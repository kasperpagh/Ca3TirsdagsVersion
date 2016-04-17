/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import java.util.TimerTask;

/**
 *
 * @author kaspe
 */
public class TimerStarter extends TimerTask
{

    @Override
    public void run()
    {
        new Thread(new XmlReader()).start();
    }


}
