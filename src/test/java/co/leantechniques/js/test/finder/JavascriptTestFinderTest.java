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

import co.leantechniques.js.test.finder.mock.MockProject;
import co.leantechniques.js.test.finder.mock.psi.MockPsiElement;
import co.leantechniques.js.test.finder.mock.psi.MockPsiFile;
import com.intellij.psi.PsiElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JavascriptTestFinderTest {
    @Mock
    private ServiceManagerProxy serviceManagerProxy;
    @Mock
    private FilenameExtensionFactory filenameExtensionFactory;
    @Mock
    private ProjectFileRepository projectFileRepository;
    @InjectMocks
    private JavascriptTestFinder finder;
    private MockPsiElement currentCursorPosition;
    private MockProject project;
    private MockPsiFile containingFile;
    private List<String> testSuffixes;
    private List<String> prodSuffixes;

    @Before
    public void setUp() throws Exception {
        project = new MockProject();
        containingFile = new MockPsiFile();

        currentCursorPosition = new MockPsiElement();
        currentCursorPosition.setProject(project);
        currentCursorPosition.setContainingFile(containingFile);

        testSuffixes = Arrays.asList("test");
        prodSuffixes = Arrays.asList("prod");

        when(serviceManagerProxy.get(project, FilenameExtensionFactory.class)).thenReturn(filenameExtensionFactory);
        when(serviceManagerProxy.get(project, ProjectFileRepository.class)).thenReturn(projectFileRepository);
        when(filenameExtensionFactory.getProductionFileExtensions()).thenReturn(prodSuffixes);
        when(filenameExtensionFactory.getTestFileExtensions()).thenReturn(testSuffixes);
    }

    @Test
    public void shouldSearchForTheParentElementsForTheSourceElement() {
        assertSame(containingFile, finder.findSourceElement(currentCursorPosition));
    }

    @Test
    public void shouldReturnFalseIfTheTheCurrentFileDoesNotEndWithAnyOfTheSuffixes() {
        whenTestSuffixesAre("test", "test2");
        containingFile.setName("doesNotMatchAnything");

        assertFalse(finder.isTest(currentCursorPosition));
    }

    @Test
    public void shouldReturnTrueIfTheTheCurrentFileEndWithATestSuffix() {
        whenTestSuffixesAre("test");
        containingFile.setName("blah-test");

        assertTrue(finder.isTest(currentCursorPosition));
    }

    @Test
    public void shouldQueryForTheTestFiles() {
        List<PsiElement> expected = new ArrayList<PsiElement>();
        expected.add(new MockPsiElement());

        when(projectFileRepository.findMatchingFilesTo(containingFile, prodSuffixes, testSuffixes)).thenReturn(expected);

        assertSame(expected, finder.findTestsForClass(currentCursorPosition));
    }

    @Test
    public void shouldQueryForTheProductionFiles() {
        List<PsiElement> expected = new ArrayList<PsiElement>();
        expected.add(new MockPsiElement());

        when(projectFileRepository.findMatchingFilesTo(containingFile, testSuffixes, prodSuffixes)).thenReturn(expected);

        assertSame(expected, finder.findClassesForTest(currentCursorPosition));
    }

    private void whenTestSuffixesAre(String... suffixes) {
        when(filenameExtensionFactory.getTestFileExtensions()).thenReturn(Arrays.asList(suffixes));
    }
}