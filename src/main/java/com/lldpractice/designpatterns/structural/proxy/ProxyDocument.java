package com.lldpractice.designpatterns.structural.proxy;

import com.lldpractice.designpatterns.structural.proxy.document.Document;
import com.lldpractice.designpatterns.structural.proxy.document.RealDocument;
import com.lldpractice.designpatterns.structural.proxy.proxies.LoggingProxy;
import com.lldpractice.designpatterns.structural.proxy.proxies.ProtectionProxy;
import com.lldpractice.designpatterns.structural.proxy.proxies.VirtualDocumentProxy;

import java.nio.file.AccessDeniedException;







public class ProxyDocument {
    public static void main(String[] args) throws AccessDeniedException {

//        System.out.println("=== Test 1: Virtual Proxy (Lazy Loading) ===");
//        Document doc1 = new VirtualDocumentProxy("document1.pdf");
//        System.out.println(doc1.getMetadata());  // Fast! No loading
//        doc1.view();  // NOW it loads (2 seconds)
//
//        System.out.println("\n=== Test 2: Protection Proxy ===");
//        Document viewerDoc = new ProtectionProxy(
//                new RealDocument("document2.pdf"),
//                "VIEWER"
//        );
//        viewerDoc.view();  // ✅ Allowed
//        try {
//            viewerDoc.edit("text");  // ❌ Should throw exception
//        } catch (AccessDeniedException e) {
//            System.out.println("VIEWER blocked from editing: " + e.getMessage());
//        }
//
//        System.out.println("\n=== Test 3: Logging Proxy ===");
//        Document loggedDoc = new LoggingProxy(
//                new RealDocument("document3.pdf"),
//                "user123"
//        );
//        loggedDoc.view();  // Logs + views
//
//        System.out.println("\n=== Test 4: ALL PROXIES COMBINED ===");
//        Document combinedDoc = new VirtualDocumentProxy("secret.pdf");
        Document combinedDoc =  new RealDocument("test.pdf");
        combinedDoc = new ProtectionProxy(combinedDoc, "EDITOR");
        combinedDoc = new LoggingProxy(combinedDoc, "user456");

        System.out.println("Getting metadata (no load):");
        System.out.println(combinedDoc.getMetadata());

        System.out.println("\nViewing document (loads now):");
        combinedDoc.view();
        // Flow: Logging → Protection → Virtual (loads) → Real

        System.out.println("\nEditing document:");
        combinedDoc.edit("New content");
        // Flow: Logging → Protection (checks) → Virtual → Real
    }
}





//public class ProxyDocument {
//
//    public static void main(String args[]) throws AccessDeniedException {
////        Document document = new VirtualDocumentProxy("document1.pdf");
////        System.out.println(document.getMetadata());
////        document.view();
////
////        RealDocument realDoc = new RealDocument("document2.pdf");
////        System.out.println("Protection Proxy: Access control....");
//
////        Document viewerDoc = new ProtectionProxy(realDoc, "VIEWER");
////        viewerDoc.view();
////        viewerDoc.edit("text");
////        viewerDoc.delete();
//
//
////        Document adminDoc = new ProtectionProxy(realDoc, "ADMIN");
////        adminDoc.view();
////        adminDoc.edit("text");
////        adminDoc.delete();
////
////        System.out.println("Logging Proxy...");
////
////        Document loggedDoc = new LoggingProxy(realDoc, "user123");
////        loggedDoc.view();  // Logs: [2025-11-25 19:45] user123 viewed report.pdf
////        loggedDoc.edit("new");
//
//
//        Document document = new RealDocument("document1.pdf");
//      document = new VirtualDocumentProxy("secret.pdf");  // Lazy load
//        document = new ProtectionProxy(document, "EDITOR");  // Access control
//        document = new LoggingProxy(document, "user456");  // Logging
//        document.delete();  // Lazy loads + checks permission + logs
//
//    }
//}
