/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package character_sets

import anorm.SqlStringInterpolation
import java.sql.Connection

object CharacterSetsViewRepoImpl extends CharacterSetsViewRepo {
  override def selectAll(implicit c: Connection): List[CharacterSetsViewRow] = {
    SQL"""select "character_set_catalog", "character_set_schema", "character_set_name", character_repertoire, form_of_use, default_collate_catalog, default_collate_schema, default_collate_name
          from information_schema.character_sets
       """.as(CharacterSetsViewRow.rowParser(1).*)
  }
}