/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.trino.plugin.vertica;

import io.trino.testing.AbstractTestQueryFramework;
import io.trino.testing.QueryRunner;
import org.testng.annotations.Test;

public class TestVerticaQueries
        extends AbstractTestQueryFramework
{
    @Override
    protected QueryRunner createQueryRunner()
            throws Exception
    {
        return VerticaQueryRunner.createQueryRunner();
    }

    @Test
    public void showTables()
    {
        assertQuery("SHOW SCHEMAS FROM vertica LIKE 't%'", "VALUES 'trino'");
        assertQuery("SHOW TABLES FROM vertica.trino", "VALUES 'test'");
    }

    @Test
    public void createAndDropTable()
    {
        assertQuerySucceeds("create table vertica.trino.test2 (a int, b date, c varchar)");
        assertQuery("SHOW TABLES FROM vertica.trino", "VALUES 'test','test2'");
        assertQuerySucceeds("drop table vertica.trino.test2");
        assertQuery("SHOW TABLES FROM vertica.trino", "VALUES 'test'");
    }

    @Test
    public void selectFromTable()
    {
        assertQuery("SELECT DISTINCT v FROM vertica.trino.test", "VALUES 'Vertica', 'Trino'");
    }
}
