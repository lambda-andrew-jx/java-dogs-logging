package com.lambdaschool.dogsinitial;

public class MessageDetail
{
    private String text;

    public MessageDetail()
    {

    }

    public MessageDetail(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "MessageDetail{" + "text='" + text + '\'' + '}';
    }
}
