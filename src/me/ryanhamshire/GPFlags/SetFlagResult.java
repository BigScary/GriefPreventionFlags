package me.ryanhamshire.GPFlags;

class SetFlagResult
{
    boolean success;
    MessageSpecifier message;
    
    SetFlagResult(boolean success, MessageSpecifier message)
    {
        this.success = success;
        this.message = message;
    }
    
    SetFlagResult(boolean success, Messages messageID, String ... args)
    {
        this.success = success;
        this.message = new MessageSpecifier(messageID, args);
    }
}
