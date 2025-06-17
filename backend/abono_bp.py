from flask import Blueprint, request, jsonify
from db import get_abonos, get_abono, create_abono, update_abono, delete_abono

abono_bp = Blueprint('abono', __name__)

@abono_bp.route('', methods=['GET'])
def get_all():
    abonos = get_abonos()
    return jsonify(abonos), 200

@abono_bp.route('/<int:id_abono>', methods=['GET'])
def get_one(id_abono):
    abono = get_abono(id_abono)
    if abono:
        return jsonify(abono), 200
    return jsonify({"error": "Abono not found"}), 404

@abono_bp.route('', methods=['POST'])
def create():
    data = request.json
    required = ['id_cuenta_por_cobrar', 'fecha', 'monto']
    
    if not all(key in data for key in required):
        return jsonify({"error": "Missing required fields"}), 400
    
    new_id = create_abono(
        id_cuenta_por_cobrar=data['id_cuenta_por_cobrar'],
        fecha=data['fecha'],
        monto=data['monto']
    )
    return jsonify({"id_abono": new_id}), 201

@abono_bp.route('/<int:id_abono>', methods=['PUT'])
def update(id_abono):
    data = request.json
    if not data:
        return jsonify({"error": "No data provided"}), 400
    
    affected = update_abono(id_abono, **data)
    if affected > 0:
        return jsonify({"message": "Abono updated"}), 200
    return jsonify({"error": "Abono not found"}), 404

@abono_bp.route('/<int:id_abono>', methods=['DELETE'])
def delete(id_abono):
    affected = delete_abono(id_abono)
    if affected > 0:
        return jsonify({"message": "Abono deleted"}), 200
    return jsonify({"error": "Abono not found"}), 404