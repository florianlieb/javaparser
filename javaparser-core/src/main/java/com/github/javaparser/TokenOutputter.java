/**
 * Original File: TokenOutputter.java
 * Original Author: lieflo1397
 * Project: XTrafo2Java
 * Created on: 27.01.2011
 * Copyright: 2011 PASS Consulting Group
 * 
 * VSS: $header: $
 * 
 * Versions:
 *   1.0 (lieflo1397)
 */
package com.github.javaparser;

import java.io.IOException;
import java.io.Writer;



/**
 * @author lieflo1397
 *
 */
public class TokenOutputter
{
	private final Writer w;
	private int nColumn = 1;
	private int nRow = 1;
	private int nDelta = 0;

	/**
	 * @param w
	 */
	public TokenOutputter(Writer w)
	{
		this.w = w;
	}
	
	
	public void write(Token t)
	{
		try
		{
			if (t.specialToken != null)
				write(t.specialToken);
			
			if (t.beginLine > nRow)
			{
				while (t.beginLine > nRow)
				{
					if (!t.ignore)
						w.write('\n');
					nRow++;
				}
				nColumn = 1;
				nDelta = 0;
			}
				
			int gap = t.beginColumn - nColumn - nDelta;
			if (!t.ignore)
			{
				for (int i=0; i<gap; i++)
					w.write(' ');
			}
			
			String strImage = t.image.trim();
			nRow+=t.endLine-t.beginLine;
			
			if (!t.ignore)
				w.write(strImage);
			
			nColumn += strImage.length() + gap;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	public void write(String str, int beginLine, int beginColumn, int delta)
	{
		try
		{
			if (beginLine > nRow)
			{
				while (beginLine > nRow)
				{
					w.write('\n');
					nRow++;
				}
				nColumn = 1;
				nDelta = 0;
			}
			
			int gap = beginColumn - nColumn - nDelta;
			for (int i=0; i<gap; i++)
				w.write(' ');
			
			w.write(str);
			nColumn += str.length() + gap;
			nDelta += delta;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public void writeln(String str)
	{
		try
		{
			w.write(str);
			w.write('\n');
			nColumn = 1;
			nDelta = 0;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public void endln()
	{
		try
		{
			w.write('\n');
			nColumn = 1;
			nDelta = 0;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
}
