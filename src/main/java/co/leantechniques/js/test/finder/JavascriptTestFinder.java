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

import com.intellij.psi.PsiElement;
import com.intellij.testIntegration.TestFinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class JavascriptTestFinder implements TestFinder {
    private ServiceManagerProxy serviceManagerProxy = new ServiceManagerProxy();

    @Nullable
    public PsiElement findSourceElement(PsiElement psiElement) {
        return psiElement.getContainingFile();
    }

    @NotNull
    public Collection<PsiElement> findTestsForClass(@NotNull PsiElement psiElement) {
        FilenameExtensionFactory filenameExtensionFactory = filenameExtensionFactory(psiElement);
        Collection<String> testSuffixes = filenameExtensionFactory.getTestFileExtensions();
        Collection<String> prodSuffixes = filenameExtensionFactory.getProductionFileExtensions();
        return fileRepository(psiElement).findMatchingFilesTo(psiElement.getContainingFile(), prodSuffixes, testSuffixes);
    }

    @NotNull
    public Collection<PsiElement> findClassesForTest(@NotNull PsiElement psiElement) {
        FilenameExtensionFactory filenameExtensionFactory = filenameExtensionFactory(psiElement);
        Collection<String> testSuffixes = filenameExtensionFactory.getTestFileExtensions();
        Collection<String> prodSuffixes = filenameExtensionFactory.getProductionFileExtensions();
        return fileRepository(psiElement).findMatchingFilesTo(psiElement.getContainingFile(), testSuffixes, prodSuffixes);
    }

    public boolean isTest(@NotNull PsiElement psiElement) {
        FilenameExtensionFactory filenameExtensionFactory = filenameExtensionFactory(psiElement);
        Collection<String> extensions = filenameExtensionFactory.getTestFileExtensions();
        String filename = psiElement.getContainingFile().getName();

        for (String testFileExtension : extensions) {
            if (filename.endsWith(testFileExtension)) {
                return true;
            }
        }

        return false;
    }

    private FilenameExtensionFactory filenameExtensionFactory(@NotNull PsiElement psiElement) {
        return serviceManagerProxy.get(psiElement.getProject(), FilenameExtensionFactory.class);
    }

    private ProjectFileRepository fileRepository(PsiElement psiElement) {
        return serviceManagerProxy.get(psiElement.getProject(), ProjectFileRepository.class);
    }
}
