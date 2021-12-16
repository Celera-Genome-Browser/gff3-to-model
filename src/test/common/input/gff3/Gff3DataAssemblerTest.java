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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.List;

import org.junit.*;

import oss.model.builder.gff3.Gff3DataAssembler;
import oss.model.builder.gff3.Gff3GenericModel;
import oss.model.builder.gff3.ModelTreeNode;

public class Gff3DataAssemblerTest {
	private static String TEST_FILE = "test/A_fumigatus_Af293.gff";
	private static String TEST_FILE_2 = "test/discoidium_chr_1.gff";
	
	@Test
	public void digest() {
		try {
			File f = getInputStream( TEST_FILE);
			
			Gff3DataAssembler assembler = new Gff3DataAssembler( f.getAbsolutePath() );
			dumpAxes( assembler );
			assembler.prepareModels( "A_fumigatus_Af293_Chr1" );
			List<ModelTreeNode> featureNodes = assembler.getTopLevelFeatures();
			if ( featureNodes.size() == 0 )
				fail("No features found.");
			StringBuilder bldr = new StringBuilder();
			int breakCount = 0;
			int totalChildCount = 0;
			for ( ModelTreeNode node: featureNodes ) {
				bldr.append( node.getId() );
				Gff3GenericModel model = node.getModel();
				bldr.append(" aligns to: ").append( model.getLandmarkId() ).append( ", name=").append(model.getName())
				    .append(" NOTE: ").append(model.getNote()).append(", type=").append(model.getType());
				totalChildCount += node.getChildren() == null ? 0 : node.getChildren().size();
				System.out.println(bldr.toString());
				if ( breakCount++ >= 50 )
					break;
				bldr.setLength( 0 );
			}
			if ( totalChildCount == 0 ) {
				fail("Child nodes not being associated with parent nodes.");
			}
			else {
				System.out.println( "Total child nodes " + totalChildCount );
			}
			
			Gff3DataAssembler assembler2 = new Gff3DataAssembler( "g:\\docs\\genomes\\burkholderia\\ATCC_23344_NC_006348.gff" );
			dumpAxes(assembler2);

		} catch ( Exception ex ) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	@Test
	public void altFileTypeTest() {
		try {
			File f = getInputStream( TEST_FILE_2 );
			Gff3DataAssembler assembler = new Gff3DataAssembler( f.getAbsolutePath() );
			dumpAxes( assembler );
			assembler.prepareModels( "DDB0232428" );

			List<ModelTreeNode> featureNodes = assembler.getTopLevelFeatures();
			if ( featureNodes.size() == 0 )
				fail("No features found.");
			
		} catch ( Exception ex ) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
	}

	private File getInputStream( String testFile ) throws FileNotFoundException {
		InputStream is = this.getClass().getResourceAsStream( testFile );
		File f = null;
		if ( is == null ) {
			f = new File( testFile );
			//is = new FileInputStream( f );
		}
		return f;
	}
	
	private void dumpAxes(Gff3DataAssembler assembler) {
		List<Gff3GenericModel> axisModels = assembler.getAxisModels();
		for ( Gff3GenericModel axis: axisModels ) {
			System.out.println("Axis: " + axis.getLandmarkId() );
		}
		if ( axisModels.size() == 0 ) {
			fail( "No axis IDs found" );
		}

	}
}
