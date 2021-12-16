package common.input.gff3;

import oss.model.builder.gff3.Gff3DataAssembler;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

public class TestUtils {
    static String resolveToPath(String testFile ) {
        return resolveFileLoc(testFile).getAbsolutePath();
    }

    static File resolveFileLoc(String testFile ) {
        return Optional.ofNullable(Gff3DataAssembler.class.getResource(testFile))
                .map(u -> toUri(u))
                .map(uri -> new File(uri))
                .orElse(new File(testFile)
                );
    }

    private static URI toUri(URL url) {
        try {
            return url.toURI();
        } catch (URISyntaxException use) {
            return null;
        }
    }


}
