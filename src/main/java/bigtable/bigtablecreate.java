package bigtable;

import com.google.cloud.bigtable.hbase.BigtableConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;


import com.google.cloud.bigtable.hbase.BigtableConfiguration;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class bigtablecreate {


    // Refer to table metadata names by byte array in the HBase API
    private static final byte[] TABLE_NAME = Bytes.toBytes("tweet2020");
    private static final byte[] COLUMN_FAMILY_NAME = Bytes.toBytes("cf1");
    private static final byte[] COLUMN_NAME = Bytes.toBytes("greeting");

    // Write some friendly greetings to Cloud Bigtable
    private static final String[] GREETINGS =
            {"Hello World!", "Hello Cloud Bigtable!", "Hello HBase!"};

    /**
     * Connects to Cloud Bigtable, runs some basic operations and prints the results.
     */
    private static void doHelloWorld(String projectId, String instanceId) {

        // [START connecting_to_bigtable]
        // Create the Bigtable connection, use try-with-resources to make sure it gets closed
        try (Connection connection = BigtableConfiguration.connect(projectId, instanceId)) {

            // The admin API lets us create, manage and delete tables
            Admin admin = connection.getAdmin();
            // [END connecting_to_bigtable]

            // [START creating_a_table]
            // Create a table with a single column family
            HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
            descriptor.addFamily(new HColumnDescriptor(COLUMN_FAMILY_NAME));

            Table table = connection.getTable(TableName.valueOf(TABLE_NAME));
            if (!admin.tableExists(TableName.valueOf(TABLE_NAME))) {
                print("Create table " + descriptor.getNameAsString());
                admin.createTable(descriptor);
                // [END creating_a_table]

                // [START writing_rows]
                // Retrieve the table we just created so we can do some reads and writes


                // Write some rows to the table
                print("Write some greetings to the table");
                for (int i = 0; i < GREETINGS.length; i++) {


                    String rowKey = "greeting" + i;

                    // Put a single row into the table. We could also pass a list of Puts to write a batch.
                    Put put = new Put(Bytes.toBytes(rowKey));
                    put.addColumn(COLUMN_FAMILY_NAME, COLUMN_NAME, Bytes.toBytes(GREETINGS[i]));

                    table.put(put);
                }
                // [END writing_rows]

                // [START getting_a_row]
                // Get the first greeting by row key
                String rowKey = "iphone11";
                Result getResult = table.get(new Get(Bytes.toBytes(rowKey)));
                String greeting = Bytes.toString(getResult.getValue(COLUMN_FAMILY_NAME, COLUMN_NAME));
                System.out.println("Get a single greeting by row key");
                System.out.printf("\t%s = %s\n", rowKey, greeting);
                // [END getting_a_row]

                // [START scanning_all_rows]
                // Now scan across all rows.
                Scan scan = new Scan();

                print("Scan for all greetings:");
                ResultScanner scanner = table.getScanner(scan);
                for (Result row : scanner) {
                    byte[] valueBytes = row.getValue(COLUMN_FAMILY_NAME, COLUMN_NAME);
                    System.out.println('\t' + Bytes.toString(valueBytes));
                }
                // [END scanning_all_rows]


            } else {
                System.out.println("Table exists");
                print("Delete the table");
                admin.disableTable(table.getName());
                admin.deleteTable(table.getName());
            }
            // [START deleting_a_table]
            // Clean up by disabling and then deleting the table

            // [END deleting_a_table]

        } catch (IOException e) {
            System.err.println("Exception while running HelloWorld: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }

    private static void print(String msg) {
        System.out.println("HelloWorld: " + msg);
    }

    public static void main(String[] args) {
        // Consult system properties to get project/instance
        String projectId = "assigntin2019";
        String instanceId = "ads2019cxt";

        doHelloWorld(projectId, instanceId);
    }


    private static String requiredProperty(String prop) {
        String value = System.getProperty(prop);
        if (value == null) {
            throw new IllegalArgumentException("Missing required system property: " + prop);
        }
        return value;
    }



}

