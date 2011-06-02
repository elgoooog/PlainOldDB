package com.elgoooog.podb.test.binding;

import com.elgoooog.podb.Database;
import com.elgoooog.podb.model.binding.Binding;
import com.elgoooog.podb.model.fields.SqlField;
import com.elgoooog.podb.test.objects.Held;
import com.elgoooog.podb.test.fields.HeldSqlField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Nicholas Hauschild
 *         Date: 5/29/11
 *         Time: 3:25 PM
 */
public class HeldBinding extends Binding {
    @Override
    public SqlField newSqlField(Object value, int index) {
        return new HeldSqlField(index, (Held)value);
    }

    @Override
    public Object getValue(ResultSet rs, String key, Database database) throws SQLException {
        int id = rs.getInt(key);
        Collection<Held> theHeld = database.read(Held.class);
        for(Held held : theHeld) {
            if(held.getId() == id) {
                return held;
            }
        }
        throw new RuntimeException("Held not found:" + id);
    }

    @Override
    public Class<?> getBindingClass() {
        return Held.class;
    }
}
