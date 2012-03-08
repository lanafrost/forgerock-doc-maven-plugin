/*
 * MPL 2.0 HEADER START
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * If applicable, add the following below this MPL 2.0 HEADER, replacing
 * the fields enclosed by brackets "[]" replaced with your own identifying
 * information:
 *     Portions Copyright [yyyy] [name of copyright owner]
 *
 * MPL 2.0 HEADER END
 *
 *     Copyright 2012 ForgeRock AS
 *
 */

package org.forgerock.doc.maven;



import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;



/**
 * Utility methods to prepare built HTML docs for publication.
 */
public class HTMLUtils
{
  /**
   * Add a <code>.htaccess</code> file to each directory for publication on an
   * Apache HTTPD server.
   *
   * @param baseDir
   *          Base directory under which to add files recursively
   * @param htaccess
   *          <code>.htaccess</code> file to copy
   * @return Directories to which the file was copied
   * @throws IOException
   *           Something went wrong during copy procedure.
   */
  public static List<File> addHtaccess(String baseDir, File htaccess)
      throws IOException
  {
    RecursiveFileCopier rcf = new RecursiveFileCopier(htaccess);
    return rcf.add(new File(baseDir));
  }



  /**
   * Add <code>css/coredoc.css</code> in each subdirectory containing document
   * files. The entry point is typically <code>index.html</code>, though it can
   * be different. You pass the <code>coredoc.css</code> file. This method adds
   * the <code>css/</code> subdirectory.
   *
   * @param baseDir
   *          Base directory under which to find subdirectories
   * @param cssFile
   *          <code>coredoc.css</code> file to copy
   * @param entryPoint
   *          Entry point file for a document, next to which to copy
   *          <code>css/coredoc.css</code>
   * @return Directories to which <code>css/coredoc.css</code> was copied
   * @throws IOException
   *           Something went wrong during copy procedure.
   */
  public static List<File> addCss(String baseDir, File cssFile,
      final String entryPoint) throws IOException
  {
    FilenameFilter filter = new FilenameFilter()
    {
      @Override
      public boolean accept(File dir, String name)
      {
        return name.endsWith(entryPoint);
      }
    };

    FilteredRecursiveFileCopier frfc = new FilteredRecursiveFileCopier(
        cssFile, filter, "css");
    return frfc.add(new File(baseDir));
  }



  /**
   * Replace an HTML tag with content from a template file.
   *
   * @param baseDir
   *          Base directory under which to find HTML files recursively
   * @param tagToReplace
   *          Tag to replace in HTML
   * @param replacement
   *          Content used to replace tag. Includes tag itself.
   * @return List of files updated
   * @throws IOException
   *           Something went wrong reading or writing files.
   */
  static List<File> updateHTML(String baseDir, String tagToReplace,
      String replacement) throws IOException
  {
    // Match normal directories, and HTML files.
    IOFileFilter dirFilter = FileFilterUtils.and(
        FileFilterUtils.directoryFileFilter(),
        HiddenFileFilter.VISIBLE);
    IOFileFilter fileFilter = FileFilterUtils.and(
        FileFilterUtils.fileFileFilter(),
        FileFilterUtils.suffixFileFilter(".html"));
    FileFilter filter = FileFilterUtils.or(dirFilter, fileFilter);

    FilteredFileUpdater ffu = new FilteredFileUpdater(tagToReplace,
        replacement, filter);
    return ffu.update(new File(baseDir));
  }



  /**
   * Add DOCTYPE declaration to HTML files.
   *
   * @param baseDir
   *          Base directory under which to find HTML files recursively
   * @param template
   *          DOCTYPE declaration + HTML start tag to replace existing HTML
   *          start tag
   * @return HTML files updated
   * @throws IOException
   *           Something went wrong reading or writing files.
   */
  public static List<File> addDoctype(String baseDir, String template)
      throws IOException
  {
    return updateHTML(baseDir, "<html>", template);
  }



  /**
   * Add JavaScript before &lt;/head&gt; in HTML files.
   *
   * @param baseDir
   *          Base directory under which to find HTML files recursively
   * @param template
   *          JavaScript + HEAD end tag to replace existing HEAD end tag
   * @throws IOException
   *           Something went wrong reading or writing files.
   */
  public static List<File> addJavaScript(String baseDir,
      String template) throws IOException
  {
    return updateHTML(baseDir, "</head>", template);
  }



  /**
   * Add favicon link before &lt;/head&gt; in HTML files.
   *
   * @param baseDir
   *          Base directory under which to find HTML files recursively
   * @param template
   *          Favicon link + HEAD end tag to replace existing HEAD end tag
   * @throws IOException
   *           Something went wrong reading or writing files.
   */
  public static List<File> addFavicon(String baseDir, String template)
      throws IOException
  {
    return updateHTML(baseDir, "</head>", template);
  }



  /**
   * Add Google Analytics script before &lt;/body&gt; in HTML files.
   *
   * @param baseDir
   *          Base directory under which to find HTML files recursively
   * @param id
   *          Google Analytics ID for the project
   * @param template
   *          Google Analytics script template with ANALYTICS-ID in place of the
   *          actual ID + BODY end tag to replace existing BODY end tag
   * @throws IOException
   *           Something went wrong reading or writing files.
   */
  public static List<File> addGoogleAnalytics(String baseDir,
      String id, String template) throws IOException
  {
    template.replace("ANALYTICS-ID", id);
    return updateHTML(baseDir, "</body>", template);
  }
}
