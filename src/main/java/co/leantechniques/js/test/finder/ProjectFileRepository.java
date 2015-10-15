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
import com.intellij.psi.PsiFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

public class ProjectFileRepository {
    private ProjectFilenameIndex projectFilenameIndex;

    public ProjectFileRepository(ProjectFilenameIndex projectFilenameIndex) {
        this.projectFilenameIndex = projectFilenameIndex;
    }

    public List<PsiElement> findMatchingFilesTo(PsiFile psiFile, Collection<String> suffixesToExclude, Collection<String> suffixesToFind) {
        String filename = psiFile.getName();
        Collection<String> filesToSearchFor = determinePossibleFilenamesFor(filename, suffixesToExclude, suffixesToFind);
        return searchFor(filesToSearchFor);
    }

    private List<PsiElement> searchFor(Collection<String> filesToSearchFor) {
        List<PsiElement> matches = new ArrayList<PsiElement>();
        for (String filenameToSearchFor : filesToSearchFor) {
            matches.addAll(projectFilenameIndex.getFilesByName(filenameToSearchFor));
        }

        return matches;
    }

    private Collection<String> determinePossibleFilenamesFor(String filename, Collection<String> suffixesToExclude, Collection<String> suffixesToFind) {
        LinkedHashSet<String> filesToSearchFor = new LinkedHashSet<String>();
        for (String exclude : suffixesToExclude) {
            String excludeFromFilename = filename.replace(exclude, "");
            if (excludeFromFilename.length() < filename.length()) {
                for (String find : suffixesToFind) {
                    filesToSearchFor.add(excludeFromFilename + find);
                }
            }
        }
        return filesToSearchFor;
    }
}
