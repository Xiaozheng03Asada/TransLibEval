import networkx as nx

class GraphUtils:
    def find_connected_components(self, node_count: int, edge_count: int, edges: str) -> str:
        G = nx.Graph()

        if edges:
            edge_list = edges.split(",")
            for edge in edge_list:
                node1, node2 = edge.split("-")
                G.add_edge(node1.strip(), node2.strip())

        components = list(nx.connected_components(G))
        component_sizes = [len(component) for component in components]

        if component_sizes:
            return f"success: {len(component_sizes)} components with sizes: " + ", ".join(map(str, component_sizes))
        elif node_count > 0:
            return f"success: {node_count} components with sizes: " + ", ".join(["1"] * node_count)
        else:
            return "failed"
