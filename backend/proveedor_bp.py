from flask import Blueprint, request, jsonify
from db import get_proveedores, get_proveedor, create_proveedor, update_proveedor, delete_proveedor

proveedor_bp = Blueprint('proveedor', __name__)

@proveedor_bp.route('', methods=['GET'])
def get_all():
    proveedores = get_proveedores()
    return jsonify(proveedores), 200

@proveedor_bp.route('/<int:id_proveedor>', methods=['GET'])
def get_one(id_proveedor):
    proveedor = get_proveedor(id_proveedor)
    if proveedor:
        return jsonify(proveedor), 200
    return jsonify({"error": "Proveedor not found"}), 404

@proveedor_bp.route('', methods=['POST'])
def create():
    data = request.json
    if not data or 'nombre' not in data:
        return jsonify({"error": "Missing required data"}), 400
    
    new_id = create_proveedor(
        nombre=data['nombre'],
        direccion=data.get('direccion'),
        telefono=data.get('telefono'),
        correo=data.get('correo')
    )
    return jsonify({"id_proveedor": new_id}), 201

@proveedor_bp.route('/<int:id_proveedor>', methods=['PUT'])
def update(id_proveedor):
    data = request.json
    if not data:
        return jsonify({"error": "No data provided"}), 400
    
    affected = update_proveedor(id_proveedor, **data)
    if affected > 0:
        return jsonify({"message": "Proveedor updated"}), 200
    return jsonify({"error": "Proveedor not found"}), 404

@proveedor_bp.route('/<int:id_proveedor>', methods=['DELETE'])
def delete(id_proveedor):
    affected = delete_proveedor(id_proveedor)
    if affected > 0:
        return jsonify({"message": "Proveedor deleted"}), 200
    return jsonify({"error": "Proveedor not found"}), 404