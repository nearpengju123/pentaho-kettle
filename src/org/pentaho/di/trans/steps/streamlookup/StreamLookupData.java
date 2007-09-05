 /**********************************************************************
 **                                                                   **
 **               This code belongs to the KETTLE project.            **
 **                                                                   **
 ** Kettle, from version 2.2 on, is released into the public domain   **
 ** under the Lesser GNU Public License (LGPL).                       **
 **                                                                   **
 ** For more details, please read the document LICENSE.txt, included  **
 ** in this project                                                   **
 **                                                                   **
 ** http://www.kettle.be                                              **
 ** info@kettle.be                                                    **
 **                                                                   **
 **********************************************************************/
 

package org.pentaho.di.trans.steps.streamlookup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.hash.ByteArrayHashIndex;
import org.pentaho.di.core.hash.LongHashIndex;
import org.pentaho.di.core.logging.LogWriter;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;




/**
 * @author Matt
 * @since 24-jan-2005
 */
public class StreamLookupData extends BaseStepData implements StepDataInterface
{
    /** used to store values in used to look up things */
	public Map<RowMetaAndData, Object[]> look;
    
    public List<KeyValue> list;
	
	/** nrs of keys-values in row. */
	public int    keynrs[];
	
    /** The metadata we send out */
    public RowMetaInterface outputRowMeta;
    
	/** First row in lookup-set */
	//public Row    firstrow;
	
	/**default string converted to values...*/
	public Object nullIf[];
	
	/** Flag to indicate that we have to read lookup values from the info step */
	public boolean readLookupValues;

    /** Stores the first row of the lookup-values to later determine if the types are the same as the input row lookup values.*/
    public RowMetaInterface keyTypes;

    public RowMetaInterface keyMeta;

    public RowMetaInterface valueMeta;

    public Comparator<KeyValue> comparator;

    public ByteArrayHashIndex hashIndex;
    public LongHashIndex longIndex;

    public RowMetaInterface lookupMeta;

    public RowMetaInterface infoMeta;
   
	public int[] lookupColumnIndex;
	
	public StreamLookupData()
	{
        super();
        look = new HashMap<RowMetaAndData, Object[]>();
        hashIndex = null;
        longIndex = new LongHashIndex();
        list = new ArrayList<KeyValue>();
        
        comparator = new Comparator<KeyValue>()
        {
            public int compare(KeyValue k1, KeyValue k2)
            {
                try
                {
                    return keyMeta.compare(k1.getKey(), k2.getKey());
                }
                catch(KettleValueException e)
                {
                    LogWriter.getInstance().logError("Stream Lookup comparator", e.getMessage());
                    return 0;
                }
            }
        };
	}

}
