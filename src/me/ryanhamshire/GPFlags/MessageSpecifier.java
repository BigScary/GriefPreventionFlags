package me.ryanhamshire.GPFlags;
import me.ryanhamshire.GPFlags.Messages;

class MessageSpecifier
{
    Messages messageID;
    String [] messageParams;
    
    MessageSpecifier(Messages messageID, String ... messageParams)
    {
        this.messageID = messageID;
        this.messageParams = messageParams;
    }
}
