# db.py
import mysql.connector
from mysql.connector import Error
from typing import List, Dict, Union, Optional
from werkzeug.security import generate_password_hash, check_password_hash

# MySQL connection config
DB_CONFIG = {
    'host': 'localhost',
    'user': 'root',
    'password': 'root',
    'database': 'Ferreteria',
    'port': 3307
}

def get_connection():
    return mysql.connector.connect(**DB_CONFIG)

def execute_query(query: str, params: tuple = None, fetch: bool = False,
                 fetch_one: bool = False, lastrowid: bool = False) -> Union[List[Dict], Dict, int, None]:
    conn = get_connection()
    cursor = conn.cursor(dictionary=True)
    try:
        cursor.execute(query, params)
        if fetch:
            result = cursor.fetchall()
        elif fetch_one:
            result = cursor.fetchone()
        else:
            conn.commit()
            if lastrowid:
                result = cursor.lastrowid
            else:
                result = cursor.rowcount
        return result
    except Error as e:
        conn.rollback()
        print(f"Database error: {e}")
        raise
    finally:
        cursor.close()
        conn.close()

# ========================= USER CRUD =========================
def get_user_by_username(username: str) -> Optional[Dict]:
    query = "SELECT * FROM users WHERE username = %s"
    return execute_query(query, (username,), fetch_one=True)

def create_user(username: str, password: str, role: str) -> int:
    hashed_pw = generate_password_hash(password)
    query = "INSERT INTO users (username, password, role) VALUES (%s, %s, %s)"
    return execute_query(query, (username, hashed_pw, role), lastrowid=True)

def verify_user(username: str, password: str) -> Optional[Dict]:
    user = get_user_by_username(username)
    if user and check_password_hash(user['password'], password):
        return user
    return None

# ========================= CLIENTE CRUD =========================
def get_clientes() -> List[Dict]:
    return execute_query("SELECT * FROM cliente", fetch=True)

def get_cliente(id_cliente: int) -> Optional[Dict]:
    return execute_query("SELECT * FROM cliente WHERE id_cliente = %s", (id_cliente,), fetch_one=True)

def create_cliente(nombre: str, direccion: str = None, telefono: str = None, email: str = None) -> int:
    query = """
        INSERT INTO cliente (nombre, direccion, telefono, email)
        VALUES (%s, %s, %s, %s)
    """
    return execute_query(query, (nombre, direccion, telefono, email), lastrowid=True)

def update_cliente(id_cliente: int, **kwargs) -> int:
    fields = []
    params = []
    for key, value in kwargs.items():
        if value is not None:
            fields.append(f"{key} = %s")
            params.append(value)
    params.append(id_cliente)
    query = f"UPDATE cliente SET {', '.join(fields)} WHERE id_cliente = %s"
    return execute_query(query, tuple(params))

def delete_cliente(id_cliente: int) -> int:
    return execute_query("DELETE FROM cliente WHERE id_cliente = %s", (id_cliente,))

# ========================= PRODUCTO CRUD =========================
def get_productos() -> List[Dict]:
    return execute_query("SELECT * FROM producto", fetch=True)

def get_producto(id_producto: int) -> Optional[Dict]:
    return execute_query("SELECT * FROM producto WHERE id_producto = %s", (id_producto,), fetch_one=True)

def create_producto(nombre: str, descripcion: str, codigo_barras: str, precio_sin_iva: float,
                   precio_unitario: float, porcentaje_ganancia: float, iva: float, stock: int,
                   categoria: int, id_proveedor: int) -> int:
    query = """
        INSERT INTO producto (nombre, descripcion, codigo_barras, precio_sin_iva,
        precio_unitario, porcentaje_ganancia, iva, stock, categoria, id_proveedor)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """
    params = (nombre, descripcion, codigo_barras, precio_sin_iva, precio_unitario,
              porcentaje_ganancia, iva, stock, categoria, id_proveedor)
    return execute_query(query, params, lastrowid=True)

def update_producto(id_producto: int, **kwargs) -> int:
    fields = []
    params = []
    for key, value in kwargs.items():
        if value is not None:
            fields.append(f"{key} = %s")
            params.append(value)
    params.append(id_producto)
    query = f"UPDATE producto SET {', '.join(fields)} WHERE id_producto = %s"
    return execute_query(query, tuple(params))

def delete_producto(id_producto: int) -> int:
    return execute_query("DELETE FROM producto WHERE id_producto = %s", (id_producto,))

# ========================= CATEGORIA CRUD =========================
def get_categorias() -> List[Dict]:
    return execute_query("SELECT * FROM categoria", fetch=True)

def get_categoria(id_categoria: int) -> Optional[Dict]:
    return execute_query("SELECT * FROM categoria WHERE id_categoria = %s", (id_categoria,), fetch_one=True)

def create_categoria(nombre: str) -> int:
    return execute_query("INSERT INTO categoria (nombre) VALUES (%s)", (nombre,), lastrowid=True)

def update_categoria(id_categoria: int, nombre: str) -> int:
    return execute_query("UPDATE categoria SET nombre = %s WHERE id_categoria = %s", (nombre, id_categoria))

def delete_categoria(id_categoria: int) -> int:
    return execute_query("DELETE FROM categoria WHERE id_categoria = %s", (id_categoria,))

# ========================= PROVEEDOR CRUD =========================
def get_proveedores() -> List[Dict]:
    return execute_query("SELECT * FROM proveedor", fetch=True)

def get_proveedor(id_proveedor: int) -> Optional[Dict]:
    return execute_query("SELECT * FROM proveedor WHERE id_proveedor = %s", (id_proveedor,), fetch_one=True)

def create_proveedor(nombre: str, direccion: str = None, telefono: str = None, correo: str = None) -> int:
    query = "INSERT INTO proveedor (nombre, direccion, telefono, correo) VALUES (%s, %s, %s, %s)"
    return execute_query(query, (nombre, direccion, telefono, correo), lastrowid=True)

def update_proveedor(id_proveedor: int, **kwargs) -> int:
    fields = []
    params = []
    for key, value in kwargs.items():
        if value is not None:
            fields.append(f"{key} = %s")
            params.append(value)
    params.append(id_proveedor)
    query = f"UPDATE proveedor SET {', '.join(fields)} WHERE id_proveedor = %s"
    return execute_query(query, tuple(params))

def delete_proveedor(id_proveedor: int) -> int:
    return execute_query("DELETE FROM proveedor WHERE id_proveedor = %s", (id_proveedor,))

# ========================= TIPO_PAGO CRUD =========================
def get_tipos_pago() -> List[Dict]:
    return execute_query("SELECT * FROM tipo_pago", fetch=True)

def get_tipo_pago(id_tipo_pago: int) -> Optional[Dict]:
    return execute_query("SELECT * FROM tipo_pago WHERE id_tipo_pago = %s", (id_tipo_pago,), fetch_one=True)

def create_tipo_pago(nombre: str, descripcion: str = None) -> int:
    query = "INSERT INTO tipo_pago (nombre, descripcion) VALUES (%s, %s)"
    return execute_query(query, (nombre, descripcion), lastrowid=True)

def update_tipo_pago(id_tipo_pago: int, **kwargs) -> int:
    fields = []
    params = []
    for key, value in kwargs.items():
        if value is not None:
            fields.append(f"{key} = %s")
            params.append(value)
    params.append(id_tipo_pago)
    query = f"UPDATE tipo_pago SET {', '.join(fields)} WHERE id_tipo_pago = %s"
    return execute_query(query, tuple(params))

def delete_tipo_pago(id_tipo_pago: int) -> int:
    return execute_query("DELETE FROM tipo_pago WHERE id_tipo_pago = %s", (id_tipo_pago,))

# ========================= VENTA CRUD =========================
def get_ventas() -> List[Dict]:
    return execute_query("SELECT * FROM venta", fetch=True)

def get_venta(id_venta: int) -> Optional[Dict]:
    return execute_query("SELECT * FROM venta WHERE id_venta = %s", (id_venta,), fetch_one=True)

def create_venta(fecha: str, id_cliente: int = None, id_tipo_pago: int = None) -> int:
    query = "INSERT INTO venta (fecha, id_cliente, id_tipo_pago) VALUES (%s, %s, %s)"
    return execute_query(query, (fecha, id_cliente, id_tipo_pago), lastrowid=True)

def update_venta(id_venta: int, **kwargs) -> int:
    fields = []
    params = []
    for key, value in kwargs.items():
        if value is not None:
            fields.append(f"{key} = %s")
            params.append(value)
    params.append(id_venta)
    query = f"UPDATE venta SET {', '.join(fields)} WHERE id_venta = %s"
    return execute_query(query, tuple(params))

def delete_venta(id_venta: int) -> int:
    return execute_query("DELETE FROM venta WHERE id_venta = %s", (id_venta,))

# ========================= DETALLE_VENTA CRUD =========================
def get_detalles_venta() -> List[Dict]:
    return execute_query("SELECT * FROM detalle_venta", fetch=True)

def get_detalle_venta(id_detalle_venta: int) -> Optional[Dict]:
    return execute_query("SELECT * FROM detalle_venta WHERE id_detalle_venta = %s", (id_detalle_venta,), fetch_one=True)

def create_detalle_venta(id_venta: int, id_producto: int, cantidad: int) -> int:
    query = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad) VALUES (%s, %s, %s)"
    return execute_query(query, (id_venta, id_producto, cantidad), lastrowid=True)

def update_detalle_venta(id_detalle_venta: int, **kwargs) -> int:
    fields = []
    params = []
    for key, value in kwargs.items():
        if value is not None:
            fields.append(f"{key} = %s")
            params.append(value)
    params.append(id_detalle_venta)
    query = f"UPDATE detalle_venta SET {', '.join(fields)} WHERE id_detalle_venta = %s"
    return execute_query(query, tuple(params))

def delete_detalle_venta(id_detalle_venta: int) -> int:
    return execute_query("DELETE FROM detalle_venta WHERE id_detalle_venta = %s", (id_detalle_venta,))

# ========================= CUENTAS_POR_COBRAR CRUD =========================
def get_cuentas_por_cobrar() -> List[Dict]:
    return execute_query("SELECT * FROM cuentas_por_cobrar", fetch=True)

def get_cuenta_por_cobrar(id_cuenta: int) -> Optional[Dict]:
    return execute_query("SELECT * FROM cuentas_por_cobrar WHERE id_cuenta = %s", (id_cuenta,), fetch_one=True)

def create_cuenta_por_cobrar(id_venta: int, fecha_inicio: str, estado: str = 'Pendiente') -> int:
    query = "INSERT INTO cuentas_por_cobrar (id_venta, fecha_inicio, estado) VALUES (%s, %s, %s)"
    return execute_query(query, (id_venta, fecha_inicio, estado), lastrowid=True)

def update_cuenta_por_cobrar(id_cuenta: int, estado: str) -> int:
    return execute_query("UPDATE cuentas_por_cobrar SET estado = %s WHERE id_cuenta = %s", (estado, id_cuenta))

def delete_cuenta_por_cobrar(id_cuenta: int) -> int:
    return execute_query("DELETE FROM cuentas_por_cobrar WHERE id_cuenta = %s", (id_cuenta,))

# ========================= ABONOS CRUD =========================
def get_abonos() -> List[Dict]:
    return execute_query("SELECT * FROM abonos", fetch=True)

def get_abono(id_abono: int) -> Optional[Dict]:
    return execute_query("SELECT * FROM abonos WHERE id_abono = %s", (id_abono,), fetch_one=True)

def create_abono(id_cuenta_por_cobrar: int, fecha: str, monto: float) -> int:
    query = "INSERT INTO abonos (id_cuenta_por_cobrar, fecha, monto) VALUES (%s, %s, %s)"
    return execute_query(query, (id_cuenta_por_cobrar, fecha, monto), lastrowid=True)

def update_abono(id_abono: int, **kwargs) -> int:
    fields = []
    params = []
    for key, value in kwargs.items():
        if value is not None:
            fields.append(f"{key} = %s")
            params.append(value)
    params.append(id_abono)
    query = f"UPDATE abonos SET {', '.join(fields)} WHERE id_abono = %s"
    return execute_query(query, tuple(params))

def delete_abono(id_abono: int) -> int:
    return execute_query("DELETE FROM abonos WHERE id_abono = %s", (id_abono,))

# ========================= COMPRAS_PRODUCTO CRUD =========================
def get_compras_producto() -> List[Dict]:
    return execute_query("SELECT * FROM compras_producto", fetch=True)

def get_compra_producto(id_compra: int) -> Optional[Dict]:
    return execute_query("SELECT * FROM compras_producto WHERE id_compra = %s", (id_compra,), fetch_one=True)

def create_compra_producto(fecha_compra: str, id_producto: int, id_proveedor: int,
                          cantidad_adquirida: int, precio_compra: float) -> int:
    query = """
        INSERT INTO compras_producto (fecha_compra, id_producto, id_proveedor, cantidad_adquirida, precio_compra)
        VALUES (%s, %s, %s, %s, %s)
    """
    params = (fecha_compra, id_producto, id_proveedor, cantidad_adquirida, precio_compra)
    return execute_query(query, params, lastrowid=True)

def update_compra_producto(id_compra: int, **kwargs) -> int:
    fields = []
    params = []
    for key, value in kwargs.items():
        if value is not None:
            fields.append(f"{key} = %s")
            params.append(value)
    params.append(id_compra)
    query = f"UPDATE compras_producto SET {', '.join(fields)} WHERE id_compra = %s"
    return execute_query(query, tuple(params))

def delete_compra_producto(id_compra: int) -> int:
    return execute_query("DELETE FROM compras_producto WHERE id_compra = %s", (id_compra,))

# ========================= VIEW ACCESSORS =========================
def get_ventas_efectivo() -> List[Dict]:
    return execute_query("SELECT * FROM venta_efectivo", fetch=True)

def get_ventas_tarjeta() -> List[Dict]:
    return execute_query("SELECT * FROM venta_tarjeta", fetch=True)

def get_clientes_cdmexico() -> List[Dict]:
    return execute_query("SELECT * FROM cliente_cd_mexico", fetch=True)

def get_clientes_guadalajara() -> List[Dict]:
    return execute_query("SELECT * FROM cliente_guadalajara", fetch=True)

def get_productos_stock_alto() -> List[Dict]:
    return execute_query("SELECT * FROM producto_stock_alto", fetch=True)

def get_productos_stock_bajo() -> List[Dict]:
    return execute_query("SELECT * FROM producto_stock_bajo", fetch=True)

def get_productos_cat1_stock_alto() -> List[Dict]:
    return execute_query("SELECT * FROM producto_cat1_stock_alto", fetch=True)

def get_productos_cat2_stock_bajo() -> List[Dict]:
    return execute_query("SELECT * FROM producto_cat2_stock_bajo", fetch=True)

def get_productos_cat3_stock_medio() -> List[Dict]:
    return execute_query("SELECT * FROM producto_cat3_stock_medio", fetch=True)
