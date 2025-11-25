from flask import Blueprint, request, jsonify
from db import (
    get_productos, get_producto, create_producto, update_producto, delete_producto,
    get_productos_stock_alto, get_productos_stock_bajo,
    get_productos_cat1_stock_alto, get_productos_cat2_stock_bajo, get_productos_cat3_stock_medio,
    get_productos_by_categoria
)

producto_bp = Blueprint('producto', __name__)

@producto_bp.route('', methods=['GET'])
def get_all():
    productos = get_productos()
    return jsonify(productos), 200

@producto_bp.route('/<int:id_producto>', methods=['GET'])
def get_one(id_producto):
    producto = get_producto(id_producto)
    if producto:
        return jsonify(producto), 200
    return jsonify({"error": "Producto not found"}), 404

@producto_bp.route('', methods=['POST'])
def create():
    data = request.json
    required = ['nombre', 'descripcion', 'codigo_barras', 'precio_sin_iva', 
               'precio_unitario', 'porcentaje_ganancia', 'iva', 'stock', 
               'categoria', 'id_proveedor']
    
    if not all(key in data for key in required):
        return jsonify({"error": "Missing required fields"}), 400
    
    new_id = create_producto(**{key: data[key] for key in required})
    return jsonify({"id_producto": new_id}), 201

@producto_bp.route('/<int:id_producto>', methods=['PUT'])
def update(id_producto):
    data = request.json
    if not data:
        return jsonify({"error": "No data provided"}), 400
    
    affected = update_producto(id_producto, **data)
    if affected > 0:
        return jsonify({"message": "Producto updated"}), 200
    return jsonify({"error": "Producto not found"}), 404

@producto_bp.route('/<int:id_producto>', methods=['DELETE'])
def delete(id_producto):
    affected = delete_producto(id_producto)
    if affected > 0:
        return jsonify({"message": "Producto deleted"}), 200
    return jsonify({"error": "Producto not found"}), 404

@producto_bp.route('/categoria/<int:id_categoria>', methods=['GET'])
def get_by_category(id_categoria):
    productos = get_productos_by_categoria(id_categoria)
    # Si devuelve None o lista vacía, devolvemos lista vacía []
    if productos:
        return jsonify(productos), 200
    return jsonify([]), 200

# Fragmented views
@producto_bp.route('/stock-alto', methods=['GET'])
def get_high_stock():
    productos = get_productos_stock_alto()
    return jsonify(productos), 200

@producto_bp.route('/stock-bajo', methods=['GET'])
def get_low_stock():
    productos = get_productos_stock_bajo()
    return jsonify(productos), 200

@producto_bp.route('/categoria1-stock-alto', methods=['GET'])
def get_cat1_high_stock():
    productos = get_productos_cat1_stock_alto()
    return jsonify(productos), 200

@producto_bp.route('/categoria2-stock-bajo', methods=['GET'])
def get_cat2_low_stock():
    productos = get_productos_cat2_stock_bajo()
    return jsonify(productos), 200

@producto_bp.route('/categoria3-stock-medio', methods=['GET'])
def get_cat3_med_stock():
    productos = get_productos_cat3_stock_medio()
    return jsonify(productos), 200