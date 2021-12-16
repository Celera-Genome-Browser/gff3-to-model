/**
 *   Copyright Leslie L. Foster, 2011.
 *
 * This is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation; version 2.1 of the License.
 * 
 * This software is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this software; if not, write to the Free Software Foundation, Inc.
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 *
 */
package common.input.gff3;
import org.junit.Test;
import oss.model.builder.gff3.Gff3GenericModel;
import oss.model.builder.gff3.Gff3LineReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.fail;

public class Gff3LineReaderTest {

	private static final String TEST_FILE = "/scerevisiae_regulatory.gff";

	@Test
	public void readLines() {
		StringBuilder errs = new StringBuilder();
		Gff3GenericModel previousModel = null;
		try {
			String pwd = System.getProperty("user.dir");
			String cp = System.getenv("CLASSPATH");
			InputStream is = Gff3LineReader.class.getResourceAsStream( TEST_FILE );
			if ( is == null ) {
				File f = new File( TEST_FILE );
				is = new FileInputStream( f );
			}

			Gff3LineReader rdr = new Gff3LineReader( TEST_FILE, is );
			Gff3GenericModel model = null;
			int modelNum = 0;
			while ( null != ( model = rdr.nextLine() ) ) {
				if ( modelNum == 0 ) {
					// Test first line:
					//chrI    SGD     chromosome      1       230208  .       .       .       ID=chrI;dbxref=NCBI:NC_001133;Name=chrI
					if ( ! model.getLandmarkId().equals( "chrI" )) {
						errs.append( "Landmark does not match: first line." );
					}
					if ( !model.getSource().equals("SGD")) {
						errs.append( "Source not Publication on first line.");
					}
					if ( model.getStart() != 1 ) {
						errs.append( "Wrong start point, first line.");
					}
					if ( model.getEnd() != 230208 ) {
						errs.append( "Wrong end point, first line.");
					}
					if ( ! model.getStrand().equals( Gff3GenericModel.Strand.none ) ) {
						errs.append( "Wrong strand, first line.");
					}
					if ( ! model.getType().equals( "chromosome")) {
						errs.append( "Wrong type, first line.");
					}
					if ( ! model.getId().equals("chrI")) {
						errs.append( "Wrong ID, first line.");
					}
					if ( ! model.getName().equals("chrI")) {
						errs.append("Wrong name, first line.");
					}
					if ( ! model.getDbxref()[0].equals("NCBI:NC_001133")) {
						errs.append("Wrong DBXREF, first line. Instead contains " + model.getDbxref());
					}
				}
				previousModel = model;
				
				modelNum ++;
			}
			
			// Test last line:
			//chrXVI  Publication     TF_binding_site 911112  911124  .       +       .	ID=ABF1-binding-site-S000086009;Name=ABF1-binding-site-S000086009;Note=ABF1%20binding%20site;dbxref=SGD:S000086009
			if ( ! previousModel.getLandmarkId().equals("chrXVI")) {
				errs.append("Wrong landmark ID, last line.");
			}
			if ( ! previousModel.getNote().equals( "ABF1 binding site")) {
				errs.append("Wrong note, last line.");
			}
			
			rdr.close();
		} catch ( Exception ex ) {
			System.out.println( errs.toString() );
			ex.printStackTrace();
			fail( ex.getMessage() );
		}
		
		if ( errs.length() > 0 ) {
			fail( errs.toString() );
		}
		else {
			System.out.println("OK");
		}
	}
	
	private boolean isEqualIfNull( String x, String y ) {
		if ( x == null ) {
			return true;
		}
		else {
			return x.equals( y );
		}
	}
}
