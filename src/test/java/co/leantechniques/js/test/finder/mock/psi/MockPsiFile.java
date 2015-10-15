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
package co.leantechniques.js.test.finder.mock.psi;

import com.intellij.lang.FileASTNode;
import com.intellij.lang.Language;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiInvalidElementAccessException;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveState;
import com.intellij.psi.impl.PsiFileEx;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.search.SearchScope;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class MockPsiFile implements PsiFileEx {
    private String name;

    public MockPsiFile() {
    }

    public MockPsiFile(String name) {
        this.name = name;
    }

    @Override
    public boolean isContentsLoaded() {
        return false;
    }

    @Override
    public void onContentReload() {

    }

    @Override
    public VirtualFile getVirtualFile() {
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        this.name = name;
        return new MockPsiElement();
    }

    @Nullable
    @Override
    public ItemPresentation getPresentation() {
        return null;
    }

    @Override
    public boolean processChildren(PsiElementProcessor<PsiFileSystemItem> psiElementProcessor) {
        return false;
    }

    @Override
    public PsiDirectory getContainingDirectory() {
        return null;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @NotNull
    @Override
    public Project getProject() throws PsiInvalidElementAccessException {
        return null;
    }

    @NotNull
    @Override
    public Language getLanguage() {
        return null;
    }

    @Override
    public PsiManager getManager() {
        return null;
    }

    @NotNull
    @Override
    public PsiElement[] getChildren() {
        return new PsiElement[0];
    }

    @Override
    public PsiDirectory getParent() {
        return null;
    }

    @Override
    public PsiElement getFirstChild() {
        return null;
    }

    @Override
    public PsiElement getLastChild() {
        return null;
    }

    @Override
    public PsiElement getNextSibling() {
        return null;
    }

    @Override
    public PsiElement getPrevSibling() {
        return null;
    }

    @Override
    public PsiFile getContainingFile() throws PsiInvalidElementAccessException {
        return null;
    }

    @Override
    public TextRange getTextRange() {
        return null;
    }

    @Override
    public int getStartOffsetInParent() {
        return 0;
    }

    @Override
    public int getTextLength() {
        return 0;
    }

    @Nullable
    @Override
    public PsiElement findElementAt(int i) {
        return null;
    }

    @Nullable
    @Override
    public PsiReference findReferenceAt(int i) {
        return null;
    }

    @Override
    public int getTextOffset() {
        return 0;
    }

    @Override
    public String getText() {
        return null;
    }

    @NotNull
    @Override
    public char[] textToCharArray() {
        return new char[0];
    }

    @Override
    public PsiElement getNavigationElement() {
        return null;
    }

    @Override
    public PsiElement getOriginalElement() {
        return null;
    }

    @Override
    public boolean textMatches(@NotNull CharSequence charSequence) {
        return false;
    }

    @Override
    public boolean textMatches(@NotNull PsiElement psiElement) {
        return false;
    }

    @Override
    public boolean textContains(char c) {
        return false;
    }

    @Override
    public void accept(@NotNull PsiElementVisitor psiElementVisitor) {

    }

    @Override
    public void acceptChildren(@NotNull PsiElementVisitor psiElementVisitor) {

    }

    @Override
    public PsiElement copy() {
        return null;
    }

    @Override
    public PsiElement add(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        return null;
    }

    @Override
    public PsiElement addBefore(@NotNull PsiElement psiElement, @Nullable PsiElement psiElement1) throws IncorrectOperationException {
        return null;
    }

    @Override
    public PsiElement addAfter(@NotNull PsiElement psiElement, @Nullable PsiElement psiElement1) throws IncorrectOperationException {
        return null;
    }

    @Override
    public void checkAdd(@NotNull PsiElement psiElement) throws IncorrectOperationException {

    }

    @Override
    public PsiElement addRange(PsiElement psiElement, PsiElement psiElement1) throws IncorrectOperationException {
        return null;
    }

    @Override
    public PsiElement addRangeBefore(@NotNull PsiElement psiElement, @NotNull PsiElement psiElement1, PsiElement psiElement2) throws IncorrectOperationException {
        return null;
    }

    @Override
    public PsiElement addRangeAfter(PsiElement psiElement, PsiElement psiElement1, PsiElement psiElement2) throws IncorrectOperationException {
        return null;
    }

    @Override
    public void delete() throws IncorrectOperationException {

    }

    @Override
    public void checkDelete() throws IncorrectOperationException {

    }

    @Override
    public void deleteChildRange(PsiElement psiElement, PsiElement psiElement1) throws IncorrectOperationException {

    }

    @Override
    public PsiElement replace(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public boolean isWritable() {
        return false;
    }

    @Nullable
    @Override
    public PsiReference getReference() {
        return null;
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        return new PsiReference[0];
    }

    @Nullable
    @Override
    public <T> T getCopyableUserData(Key<T> key) {
        return null;
    }

    @Override
    public <T> void putCopyableUserData(Key<T> key, @Nullable T t) {

    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor psiScopeProcessor, @NotNull ResolveState resolveState, @Nullable PsiElement psiElement, @NotNull PsiElement psiElement1) {
        return false;
    }

    @Nullable
    @Override
    public PsiElement getContext() {
        return null;
    }

    @Override
    public boolean isPhysical() {
        return false;
    }

    @NotNull
    @Override
    public GlobalSearchScope getResolveScope() {
        return null;
    }

    @NotNull
    @Override
    public SearchScope getUseScope() {
        return null;
    }

    @Override
    public long getModificationStamp() {
        return 0;
    }

    @NotNull
    @Override
    public PsiFile getOriginalFile() {
        return null;
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return null;
    }

    @NotNull
    @Override
    public PsiFile[] getPsiRoots() {
        return new PsiFile[0];
    }

    @NotNull
    @Override
    public FileViewProvider getViewProvider() {
        return null;
    }

    @Override
    public FileASTNode getNode() {
        return null;
    }

    @Override
    public boolean isEquivalentTo(PsiElement psiElement) {
        return false;
    }

    @Override
    public void subtreeChanged() {

    }

    @Override
    public void navigate(boolean b) {

    }

    @Override
    public boolean canNavigate() {
        return false;
    }

    @Override
    public boolean canNavigateToSource() {
        return false;
    }

    @Override
    public void checkSetName(String s) throws IncorrectOperationException {

    }

    @Override
    public Icon getIcon(int i) {
        return null;
    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T t) {

    }
}
