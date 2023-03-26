package typo
package sqlscripts

import java.nio.file.Path

case class SqlScript(relPath: Path, content: String, metadataParams: List[MetadataParameterColumn], cols: List[db.Col])

