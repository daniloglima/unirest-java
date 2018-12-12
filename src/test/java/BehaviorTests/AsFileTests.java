/**
 * The MIT License
 *
 * Copyright for portions of OpenUnirest/uniresr-java are held by Mashape (c) 2013 as part of Kong/unirest-java.
 * All other copyright for OpenUnirest/unirest-java are held by OpenUnirest (c) 2018.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package BehaviorTests;

import org.junit.Test;
import unirest.JacksonObjectMapper;
import unirest.Unirest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class AsFileTests extends BddTest {

    private Path test = Paths.get("results.json");
    private JacksonObjectMapper om = new JacksonObjectMapper();

    @Override
    public void tearDown() {
        try {
            Files.delete(test);
        } catch (Exception e) { }
    }

    @Test
    public void canSaveContentsIntoFile() {
        Unirest.get(MockServer.GET)
                .asFile(test.toString());

        om.readValue(test.toFile(), RequestCapture.class)
                .assertStatus(200);
    }

    @Test
    public void canDownloadABinaryFile() throws Exception {
        File f1 = new File(MockServer.class.getResource("/image.jpg").toURI());

        File f2 = Unirest.get(MockServer.BINARYFILE)
                .asFile(test.toString())
                .getBody();

        assertTrue(com.google.common.io.Files.equal(f1, f2));
    }
}
