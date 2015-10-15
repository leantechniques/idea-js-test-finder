/**
 *
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package co.leantechniques.js.test.finder;

import co.leantechniques.js.test.finder.mock.psi.MockPsiFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectFileRepositoryTest {
    @Mock
    private ProjectFilenameIndex projectFilenameIndex;
    private ProjectFileRepository repository;
    private MockPsiFile psiFile;

    @Before
    public void setUp() throws Exception {
        psiFile = new MockPsiFile();
        psiFile.setName("hello-world.js");

        repository = new ProjectFileRepository(projectFilenameIndex);
    }

    @Test
    public void shouldReturnAnEmptyListIfNothingWasFond() {
        assertTrue(repository.findMatchingFilesTo(psiFile, Arrays.asList(""), Arrays.asList("txt", "wav")).isEmpty());
    }

    @Test
    public void shouldNotSearchTheIndexIfNothingWasRemovedFromTheFilenameWhenDetermineWhatToSearchFor() {
        repository.findMatchingFilesTo(psiFile, Arrays.asList("doesNotExist"), Arrays.asList("txt", "wav"));

        verifyZeroInteractions(projectFilenameIndex);
    }

    @Test
    public void shouldSearchForAllFilesWithTheProvidedFileExtensions() {
        MockPsiFile txtFile = new MockPsiFile("hello-world.txt");
        MockPsiFile wavFile = new MockPsiFile("hello-world.wav");

        when(projectFilenameIndex.getFilesByName("hello-world.txt")).thenReturn(list(txtFile));
        when(projectFilenameIndex.getFilesByName("hello-world.wav")).thenReturn(list(wavFile));

        assertEquals(Arrays.asList(txtFile, wavFile), repository.findMatchingFilesTo(psiFile, Arrays.asList("js"), Arrays.asList("txt", "wav")));
    }

    @NotNull
    private List<PsiFile> list(PsiFile... files) {
        return Arrays.asList(files);
    }
}