//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-model/library/actor-cocoa-base/build/java/im/actor/model/concurrency/Command.java
//

#ifndef _AMCommand_H_
#define _AMCommand_H_

#include "J2ObjC_header.h"

@protocol AMCommandCallback;

@protocol AMCommand < NSObject, JavaObject >

- (void)startWithCallback:(id<AMCommandCallback>)callback;

@end

J2OBJC_EMPTY_STATIC_INIT(AMCommand)

J2OBJC_TYPE_LITERAL_HEADER(AMCommand)

#define ImActorModelConcurrencyCommand AMCommand

#endif // _AMCommand_H_
